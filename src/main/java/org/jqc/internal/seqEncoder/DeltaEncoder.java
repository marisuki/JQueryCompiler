package org.jqc.internal.seqEncoder;

import org.jqc.common.annotate.*;
import org.jqc.common.plugin.CompileTarget;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DeltaEncoder extends CompileTarget {

    @SharedVar List<Integer> data;
    @PrivateVar int slices;
    @SharedVar boolean littleEndian;
//    @PrivateVar int prev;
//    @PrivateVar int diff;
//    @SharedVar int minDelta;
//    @SharedVar int maxDelta;
//    @SharedVar int i;


    @Override
    @SharedVariableMap(usedVars = {"data"}, sharedVars = {"data"})
    public void initiate() {
        slices = data.size()/1000;
        if(data.size() % 1000 != 0) slices += 1;
    }

    @Override
    public void before() {

    }

    @Override
    @ParallelFor(positionOfForLoop = 1)
    public void run() {
        ByteBuffer[] buffer = new ByteBuffer[slices];
        for(int i = 0; i < slices ; i++) {
            int pos = i*1000;
            int prev = data.get(pos);
            int diff = 0;
            int minDelta = Integer.MAX_VALUE;
            int maxDelta = Integer.MIN_VALUE;
            for(int j=pos+1;j<pos+1000;j++) {
                int curr = data.get(j);
                diff = curr - prev;
                if(diff < minDelta) { minDelta = diff; }
                if(diff > maxDelta) { maxDelta = diff; }
                data.set(j, diff);
            }
            if(maxDelta-minDelta % 8 == 1) {
                buffer[i] = ByteBuffer.allocate(1000/8+12);
                buffer[i].putInt(minDelta);
                buffer[i].putInt(1);
                buffer[i].putInt(data.get(pos));
                int tmp;
                for(int j=pos+1;j<pos+1000;j+=8) {
                    tmp = 0;
                    for(int k=0;k<8;k++) {
                        int curr = data.get(j+k);
                        diff = (curr - minDelta);
                        tmp |= (byte) (diff << (7-k));
                    }
                    buffer[i].put((byte)tmp);
                }
            } else if(maxDelta-minDelta < 4) {
                buffer[i] = ByteBuffer.allocate(2000/8+12);
                buffer[i].putInt(minDelta);
                buffer[i].putInt(2);
                buffer[i].putInt(data.get(pos));
                byte tmp;
                for(int j=pos+1;j<pos+1000;j+=4) {
                    tmp = 0;
                    for(int k=0;k<4;k++) {
                        int curr = data.get(j+k);
                        diff = (curr - minDelta);
                        tmp |= (byte) (diff << ((3-k)<<1));
                    }
                    buffer[i].put(tmp);
                }
            } else if(maxDelta-minDelta < 8) {
                buffer[i] = ByteBuffer.allocate(3000/8+12);
                buffer[i].putInt(minDelta);
                buffer[i].putInt(3);
                buffer[i].putInt(data.get(pos));
                int tmp;
                for(int j=pos+1;j<pos+1000;j+=8) {
                    tmp = 0;
                    for(int k=0;k<8;k++) {
                        int curr = data.get(j+k);
                        diff = (curr - minDelta);
                        tmp |= (diff << (29-3*k));
                    }
                    buffer[i].put((byte)((tmp >> 24) & 0xFF));
                    buffer[i].put((byte)((tmp >> 16) & 0xFF));
                    buffer[i].put((byte)((tmp >> 8) & 0xFF));
                }
            } else if(maxDelta-minDelta < 16) {
                buffer[i] = ByteBuffer.allocate(4000/8+12);
                buffer[i].putInt(minDelta);
                buffer[i].putInt(4);
                buffer[i].putInt(data.get(pos));
                byte tmp;
                for(int j=pos+1;j<pos+1000;j+=2) {
                    tmp = (byte) (data.get(j) << 4 | data.get(j+1));
                    buffer[i].put(tmp);
                }
            } else if(maxDelta-minDelta < 32) {
                buffer[i] = ByteBuffer.allocate(5000/8+12);
                buffer[i].putInt(minDelta);
                buffer[i].putInt(5);
                buffer[i].putInt(data.get(pos));
            }
        }
    }

    @Override
    @SharedVariables(value = {"minDelta", "maxDelta"})
    public void after() {

    }

    @Override
    public String[] sharedVariables() {
        return new String[]{
                "int minDelta = Integer.MAX_VALUE; " +
                "int maxDelta = Integer.MIN_VALUE;"};
    }

    public static void main(String[] args) {
        DeltaEncoder encoder = new DeltaEncoder();
        List<Integer> data = new ArrayList<>();
        data.add(10);data.add(11);data.add(15);data.add(14);data.add(17);
        data.add(15);data.add(21);data.add(25);data.add(24);data.add(27);
        data.add(30);data.add(31);data.add(32);data.add(33);data.add(34);
        data.add(35);data.add(36);data.add(37);data.add(38);
        List<Byte> buffer = new ArrayList<>();

        // start
        int minDelta = Integer.MAX_VALUE;
        int maxDelta = Integer.MIN_VALUE;

        encoder.data = data;

        encoder.initiate();
        for(int i = 0; i< data.size();i++) {
            //before
            encoder.before();

            //run
            encoder.run();

            //after
            encoder.after();
        }
        for(int i = 0; i< data.size();i++) {
            System.out.println(data.get(i));
        }
    }
}
