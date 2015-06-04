package com.zhi.app.ui;

import java.text.SimpleDateFormat;
import java.util.List;

import com.zhi.app.entity.ChatMsg;
import com.zhi.app.entity.ChatMsg.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MsgAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ChatMsg> mChatMsgList;

	public MsgAdapter(Context context, List<ChatMsg> chatMsgList) {
		mInflater = LayoutInflater.from(context);
		this.mChatMsgList = chatMsgList;
	}

	@Override
	public int getCount() {
		return this.mChatMsgList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mChatMsgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		ChatMsg chatMsg = this.mChatMsgList.get(position);

		if (convertView == null) {

			viewHolder = new ViewHolder();

			if (getItemViewType(position) == 0) {

				convertView = mInflater.inflate(R.layout.item_receive_msg, null);
				viewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_receive_date);
				viewHolder.mShowText = (TextView) convertView.findViewById(R.id.tv_receive_msg);
				
			} else {
				convertView = mInflater.inflate(R.layout.item_send_msg, null);
				viewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_send_date);
				viewHolder.mShowText = (TextView) convertView.findViewById(R.id.tv_send_msg);
			}
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHolder.mDate.setText(sdf.format(chatMsg.getDate()));
		viewHolder.mShowText.setText(chatMsg.getMsg());

		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMsg chatMsg = this.mChatMsgList.get(position);
		if (chatMsg.getType() == Type.RECEIVE) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	private final class ViewHolder {
		TextView mDate;
		TextView mShowText;
	}
}
