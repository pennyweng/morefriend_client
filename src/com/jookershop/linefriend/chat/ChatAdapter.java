package com.jookershop.linefriend.chat;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.friend.FriendFragment;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class ChatAdapter extends ArrayAdapter<ChatItem> {
	    private Context mContext;
	    private SharedPreferences sp;
	    public ChatAdapter(Context context, ArrayList<ChatItem> interestItems) {
	        super(context, R.layout.chatitem, interestItems);
	        mContext = context;
	        sp = context.getSharedPreferences("linefriend", Context.MODE_APPEND);
	     }
	    
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	final ChatItem chatItem = getItem(position);
	    	if (convertView == null) {
	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chatitem, parent, false);
	         }
	    	ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
//	    	UrlImageViewHelper.setUrlDrawable(iv, Constants.BASE_URL + "chat/image?id=" + chatItem.getId());
	    	
	    	MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "chat/image?id=" + chatItem.getId(), 
	    			iv, MainActivity.options, new SimpleImageLoadingListener(), null);		    	
	    	
	    	TextView title = (TextView) convertView.findViewById(R.id.textView1);
	    	title.setText(chatItem.getName());
	    	
	    	TextView id = (TextView) convertView.findViewById(R.id.textView4);
	    	id.setText("版主帳號:" + chatItem.getLineId());
	    	
	    	TextView manCount = (TextView) convertView.findViewById(R.id.textView2);
	    	manCount.setText("男:" + chatItem.getManCount() + "人");

	    	TextView womenCount = (TextView) convertView.findViewById(R.id.textView3);
	    	womenCount.setText("女:" + chatItem.getWomenCount() + "人");
	    	
	    	Button bt = (Button) convertView.findViewById(R.id.button1);
	    	if(chatItem.getIsOpen() == 1) {
	    		bt.setBackgroundColor(Color.parseColor("#3E4678"));
	    	bt.setText("加入\n聊天");
	    	bt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(sp.contains(Constants.LINE_STORE_KEY))
						Message.ShowMsgDialog(mContext, "加入方法:\n用您的Line加入版主ID:"+ chatItem.getLineId() + "為朋友,並留言您想加入的聊天室名稱, 我們會儘快為您處理!" );
					else
						Message.ShowMsgDialog(mContext, "麻煩先完成右上方的基本資料設定" );
				}
			});	    	
	    	} else {
	    		bt.setText("尚未\n開放");
	    		bt.setBackgroundColor(Color.parseColor("#878787"));
	    	}
	    	
	    	
	    	convertView.setBackgroundColor(Color.parseColor(chatItem.getColor()));
	    	return convertView;
	    }
	 
}

