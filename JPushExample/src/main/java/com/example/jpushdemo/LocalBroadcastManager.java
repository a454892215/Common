package com.example.jpushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by efan on 2017/4/14.
 */

final class LocalBroadcastManager {
  //  private static final String TAG = "JIGUANG-Example";
 //   private static final boolean DEBUG = false;
    private final Context mAppContext;
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();
    private final HashMap<String, ArrayList<LocalBroadcastManager.ReceiverRecord>> mActions = new HashMap<>();
    private final ArrayList<LocalBroadcastManager.BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
//    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private final Handler mHandler;
    private static final Object mLock = new Object();
    private static LocalBroadcastManager mInstance;

    static LocalBroadcastManager getInstance(Context context) {
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }

            return mInstance;
        }
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    LocalBroadcastManager.this.executePendingBroadcasts();
                } else {
                    super.handleMessage(msg);
                }

            }
        };
    }

    void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
  //      HashMap var3 = this.mReceivers;
        synchronized (this.mReceivers) {
            LocalBroadcastManager.ReceiverRecord entry = new LocalBroadcastManager.ReceiverRecord(filter, receiver);
            ArrayList filters = this.mReceivers.get(receiver);
            if (filters == null) {
                filters = new ArrayList(1);
                this.mReceivers.put(receiver, filters);
            }

            filters.add(filter);

            for (int i = 0; i < filter.countActions(); ++i) {
                String action = filter.getAction(i);
                ArrayList entries = (ArrayList) this.mActions.get(action);
                if (entries == null) {
                    entries = new ArrayList(1);
                    this.mActions.put(action, entries);
                }

                entries.add(entry);
            }

        }
    }

    void unregisterReceiver(BroadcastReceiver receiver) {
      //  HashMap var2 = this.mReceivers;
        synchronized (this.mReceivers) {
            ArrayList filters = this.mReceivers.remove(receiver);
            if (filters != null) {
                for (int i = 0; i < filters.size(); ++i) {
                    IntentFilter filter = (IntentFilter) filters.get(i);

                    for (int j = 0; j < filter.countActions(); ++j) {
                        String action = filter.getAction(j);
                        ArrayList receivers = this.mActions.get(action);
                        if (receivers != null) {
                            for (int k = 0; k < receivers.size(); ++k) {
                                if (((LocalBroadcastManager.ReceiverRecord) receivers.get(k)).receiver == receiver) {
                                    receivers.remove(k);
                                    --k;
                                }
                            }

                            if (receivers.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }

            }
        }
    }

    void sendBroadcast(Intent intent) {
      //  HashMap var2 = this.mReceivers;
        synchronized (this.mReceivers) {
            String action = intent.getAction();
            String type = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set categories = intent.getCategories();
            boolean debug = (intent.getFlags() & 8) != 0;
            if (debug) {
                LogUtil.v("Resolving type " + type + " scheme " + scheme + " of intent " + intent);
            }

            ArrayList entries = this.mActions.get(intent.getAction());
            if (entries != null) {
                if (debug) {
                    LogUtil.v("Action list: " + entries);
                }

                ArrayList receivers = null;

                int i;
                for (i = 0; i < entries.size(); ++i) {
                    LocalBroadcastManager.ReceiverRecord receiver = (LocalBroadcastManager.ReceiverRecord) entries.get(i);
                    if (debug) {
                        LogUtil.v("Matching against filter " + receiver.filter);
                    }

                    if (receiver.broadcasting) {
                        if (debug) {
                            LogUtil.v("  Filter\'s target already added");
                        }
                    } else {
                        int match = receiver.filter.match(action, type, scheme, data, categories, "LocalBroadcastManager");
                        if (match >= 0) {
                            if (debug) {
                                LogUtil.v("  Filter matched!  match=0x" + Integer.toHexString(match));
                            }

                            if (receivers == null) {
                                receivers = new ArrayList();
                            }

                            receivers.add(receiver);
                            receiver.broadcasting = true;
                        } else if (debug) {
                            String reason;
                            switch (match) {
                                case -4:
                                    reason = "category";
                                    break;
                                case -3:
                                    reason = "action";
                                    break;
                                case -2:
                                    reason = "data";
                                    break;
                                case -1:
                                    reason = "type";
                                    break;
                                default:
                                    reason = "unknown reason";
                            }

                            LogUtil.v("  Filter did not match: " + reason);
                        }
                    }
                }

                if (receivers != null) {
                    for (i = 0; i < receivers.size(); ++i) {
                        ((LocalBroadcastManager.ReceiverRecord) receivers.get(i)).broadcasting = false;
                    }

                    this.mPendingBroadcasts.add(new LocalBroadcastManager.BroadcastRecord(intent, receivers));
                    if (!this.mHandler.hasMessages(1)) {
                        this.mHandler.sendEmptyMessage(1);
                    }

                }
            }

        }
    }

/*    public void sendBroadcastSync(Intent intent) {
        if (this.sendBroadcast(intent)) {
            this.executePendingBroadcasts();
        }

    }*/

    private void executePendingBroadcasts() {
        while (true) {
            LocalBroadcastManager.BroadcastRecord[] brs;
          //  HashMap i = this.mReceivers;
            synchronized (this.mReceivers) {
                int br = this.mPendingBroadcasts.size();
                if (br <= 0) {
                    return;
                }

                brs = new LocalBroadcastManager.BroadcastRecord[br];
                this.mPendingBroadcasts.toArray(brs);
                this.mPendingBroadcasts.clear();
            }

            for (BroadcastRecord var7 : brs) {
                for (int j = 0; j < var7.receivers.size(); ++j) {
                    var7.receivers.get(j).receiver.onReceive(this.mAppContext, var7.intent);
                }
            }
        }
    }

    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<LocalBroadcastManager.ReceiverRecord> receivers;

        BroadcastRecord(Intent _intent, ArrayList<LocalBroadcastManager.ReceiverRecord> _receivers) {
            this.intent = _intent;
            this.receivers = _receivers;
        }
    }

    private static class ReceiverRecord {
        final IntentFilter filter;
        final BroadcastReceiver receiver;
        boolean broadcasting;

        ReceiverRecord(IntentFilter _filter, BroadcastReceiver _receiver) {
            this.filter = _filter;
            this.receiver = _receiver;
        }

        public String toString() {
            return "Receiver{" +
                    this.receiver +
                    " filter=" +
                    this.filter +
                    "}";
        }
    }
}