# DuBluetooth

## 功能描述 

	If you have any other questions, please check here. 	<a href='http://blog.csdn.net/djy1992/article/details/10144843'> Android 蓝牙开发资料大全【新】</a>
   

## 版本 
      
    - V1.0.0 
	
		-- 根据别人修改的一个版本 --  给大家作为参考学习使用
	
	- V0.0.9
	
		-- 代码测试使用
   
## 代码示例

Please see   
	* <a href='https://github.com/sheep0704/DuBluetooth/blob/master/Bluetooth/src/com/immqy/MainActivity.java'> MainActivity</a>
	* <a href='https://github.com/sheep0704/DuBluetooth/blob/master/Bluetooth/src/com/immqy/SettingActivity.java'> SettingActivity </a>
	* <a href='https://github.com/sheep0704/DuBluetooth/blob/master/Bluetooth/src/com/immqy/SearchActivity.java'> SearchActivity </a>
	* <a href='https://github.com/sheep0704/DuBluetooth/blob/master/Bluetooth/src/com/immqy/ComminuteActivity.java'> ComminuteActivity </a>

	MainActivity ->  onActivityResult .
```java
	 //返回消息处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bluetooth.comminute(openLockBytes, 1);
                Thread.sleep(100);
                String path = BluetoothUtils.getPath(uri, MainActivity.this);
                bluetooth.sendKey((BluetoothUtils.createKey(path)).getBytes());
                Thread.sleep(100);
                lockBt.setImageDrawable(getResources().getDrawable(R.drawable.open_lock));
            } catch (Exception e) {
                Log.e("openKey", e.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
```   

	MainActivity ->   BluetoothDevice.
```java
 if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice receiverDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (isLock(receiverDevice)) {
                    device = receiverDevice;
                }
            }
            bluetooth = new Bluetooth(device, BluetoothUtils.readState());

            bluetooth.connect();
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        if (BluetoothUtils.isConnect) {
                            try {
                                if (isAutomatic) {
                                    startService(new Intent(MainActivity.this, OpenService.class));
                                    break;
                                }
                            } catch (Exception e) {
                                break;
                            }
                        }
                    }
                }
            });
            thread.start();
        }
```
   
	MainActivity ->   receiveDevice.
   
```java
	//查看
	private void receiveDevice() {
        bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BluetoothReceiver();
        registerReceiver(receiver, filter);
	} 
```

    

## Bugs and Feedback 
### For bugs, questions and discussions please use the Github Issues.
### Home: <a href='https://github.com/sheep0704/'>https://github.com/sheep0704</a>


## LICENSE 
Copyright (c) 2016-present, DuBluetooth Contributors. Author Immqy.com .

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.