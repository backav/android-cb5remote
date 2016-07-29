package li.xiangyang.android.cb5remote;

import android.app.Activity;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Vibrator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import li.xiangyang.android.cb5remote.protocol.NEC6122;

public class MainActivity extends Activity {

    ConsumerIrManager ciManager;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ciManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
        if (!ciManager.hasIrEmitter()) {
            Toast.makeText(this, "不支持红外发射器", Toast.LENGTH_LONG).show();
            finish();
        }

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    private void sendWithCode(byte code) {
        ciManager.transmit(38000, new NEC6122((short) 0xff00, code).getPattern());
    }

    public void onClick(View btn) {
        if (vibrator.hasVibrator()){
            vibrator.vibrate(100);
        }
        sendWithCode(parseCodeFromTag(btn));
        if (vibrator.hasVibrator()){
            vibrator.vibrate(50);
        }
    }

    private byte parseCodeFromTag(View btn) {
        return (byte) Integer.parseInt(btn.getTag().toString(), 16);
    }

}
