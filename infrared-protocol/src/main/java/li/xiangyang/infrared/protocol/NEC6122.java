package li.xiangyang.infrared.protocol;


/**
 * Created by bac on 16/7/26.
 */
public class NEC6122 implements IProtocol {

    private final int pulseBurst = (int) 562.5;
    private int[] pattern;

    public NEC6122(short address, byte code) {
        pattern = conact(conact(new int[]{9000, 4500}, conact(getValues(address, 0, 16),
                conact(getValues(code, 0, 8), getReverseValues(code, 0, 8)))), new int[]{pulseBurst});

    }

    private int[] getValues(int data, int from, int to) {
        int[] v = new int[(to - from) * 2];
        for (int i = from; i < to; i++) {
            v[(i - from) * 2] = pulseBurst;
            v[(i - from) * 2 + 1] = getValue(data, i);
        }
        return v;
    }

    private int[] getReverseValues(int data, int from, int to) {
        int[] v = new int[(to - from) * 2];
        for (int i = from; i < to; i++) {
            v[(i - from) * 2] = pulseBurst;
            v[(i - from) * 2 + 1] = getReverseValue(data, i);
        }
        return v;
    }

    private int[] conact(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }


    private int getValue(int data, int bit) {
        return getSpace(((data >> bit) & 0x01));
    }

    private int getReverseValue(int data, int bit) {
        return getSpace(((data >> bit) & 0x01) + 1);
    }

    private int getSpace(int value) {
        if (value == 1) {
            return (int) 1687.5;
        } else {
            return pulseBurst;
        }
    }

    @Override
    public int[] getPattern() {
        return pattern;
    }
}
