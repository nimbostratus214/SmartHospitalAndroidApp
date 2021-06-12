package radenko.mihajlovic.smarthospital;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class BindService extends Service {

    private Binder binder = null;

    public BindService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        if(binder == null) {
            binder = new Binder(getApplicationContext());
        }
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        try {
            binder.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return super.onUnbind(intent);
    }
}