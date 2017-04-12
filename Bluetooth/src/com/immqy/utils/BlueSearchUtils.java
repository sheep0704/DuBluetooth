package com.immqy.utils;

import android.app.ProgressDialog; 
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;

/**
 * 
 * 描述：蓝牙开发辅助类 -- 查询 
 * 
 * @author KARL-Dujinyang
 * @author https://github.com/sheep0704
 */
public class BlueSearchUtils { 
	
		/**
		 * 标识
		 * @param context
		 * @param handler
		 * @param message
		 * @param runnable
		 * @param dismissListener
		 */
		public static void indeterminate(Context context, Handler handler, String message, final Runnable runnable, OnDismissListener dismissListener){
			try	{
				indeterminateInternal(context, handler, message, runnable, dismissListener, true);
			}catch (Exception e)	{
				e.printStackTrace();
			}
		}

		/**
		 * 回调监听
		 * @param context
		 * @param handler
		 * @param message
		 * @param runnable
		 * @param dismissListener
		 * @param cancelable
		 */
		public static void indeterminate(Context context, Handler handler, String message, final Runnable runnable, OnDismissListener dismissListener,
			boolean cancelable)	{ 
			try	{
				indeterminateInternal(context, handler, message, runnable, dismissListener, cancelable);
			}	catch (Exception e)	{
				e.printStackTrace();
			}
		}

		private static ProgressDialog createProgressDialog(Context context, String message)		{
			ProgressDialog dialog = new ProgressDialog(context);
			dialog.setIndeterminate(false);
			dialog.setMessage(message);
			return dialog;
		}

		/**
		 * 开启线程
		 * @param context
		 * @param handler
		 * @param message
		 * @param runnable
		 * @param dismissListener
		 * @param cancelable
		 */
		private static void indeterminateInternal(Context context, final Handler handler, String message, final Runnable runnable,
			OnDismissListener dismissListener, boolean cancelable){
			final ProgressDialog dialog = createProgressDialog(context, message);
			dialog.setCancelable(cancelable);
			if (dismissListener != null){
				dialog.setOnDismissListener(dismissListener);
			}
			dialog.show();
			new Thread() {
				@Override
				public void run(){
					runnable.run();
					handler.post(new Runnable() {
						public void run(){
							try	{
								dialog.dismiss();
							}
							catch (Exception e){
								e.printStackTrace();
							}
						}
					});
				};
			}.start();
		}

}
