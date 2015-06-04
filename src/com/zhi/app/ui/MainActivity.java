package com.zhi.app.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.zhi.app.entity.ChatMsg;
import com.zhi.app.entity.ChatMsg.Type;
import com.zhi.app.utils.HttpUtils;

public class MainActivity extends Activity {

	private ListView lv_msg_record;
	private EditText et_sendMsg;
	private Button btn_send;
	private MsgAdapter adapter;
	private List<ChatMsg> chatMsgList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉顶部标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		this.initView();

		this.initData();

		this.initListener();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		lv_msg_record = (ListView) findViewById(R.id.id_listview_msgs);
		et_sendMsg = (EditText) findViewById(R.id.id_input_msg);
		btn_send = (Button) findViewById(R.id.btn_send);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		chatMsgList = new ArrayList<ChatMsg>();
		chatMsgList.add(new ChatMsg("你好，士官长", Type.RECEIVE, new Date()));

		adapter = new MsgAdapter(MainActivity.this, chatMsgList);
		lv_msg_record.setAdapter(adapter);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ChatMsg receiveMsg = (ChatMsg) msg.obj;
			chatMsgList.add(receiveMsg);
			adapter.notifyDataSetChanged();
		};
	};

	private void initListener() {

		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//获取输入框中的内容
				final String sendMsg = et_sendMsg.getText().toString();

				//在对话区域显示发送内容
				ChatMsg sendMessage = new ChatMsg();
				sendMessage.setMsg(sendMsg);
				sendMessage.setDate(new Date());
				sendMessage.setType(Type.SEND);
				chatMsgList.add(sendMessage);
				adapter.notifyDataSetChanged();
				
				//发送后清空输入框
				et_sendMsg.setText("");

				//由于获取问题的答案需要访问网络，因此将该操作放在子线程中进行
				new Thread() {
					public void run() {
						ChatMsg chatMsg = HttpUtils.sendMsg(sendMsg);

						Message message = Message.obtain();
						message.obj = chatMsg;

						handler.sendMessage(message);
					};
				}.start();
			}
		});
	}
}
