package com.example.picture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.picture.adapter.ImageAndTextListAdapter;
import com.example.picture.servlet.ImageAndText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PictureShowActivity extends Activity implements OnClickListener{
	private ImageButton ib_back5;
	private Button bt_join;
	private GridView gv_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity4_main);
		ib_back5=(ImageButton) findViewById(R.id.ib_back5);
		bt_join=(Button) findViewById(R.id.bt_join);
		gv_image=(GridView) findViewById(R.id.gv_image);
		ib_back5.setOnClickListener(this);
		bt_join.setOnClickListener(this);
//		ImageAndTextListAdapter imageAndTextListAdapter=new ImageAndTextListAdapter(this, new ArrayList<ImageAndText>(), lv_image);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("title", "大门");
		map1.put("image", R.drawable.a);
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("title", "广场");
		map2.put("image", R.drawable.a);
		Map<String,Object> map3=new HashMap<String,Object>();
		map3.put("title", "街道");
		map3.put("image", R.drawable.c);
		Map<String,Object> map4=new HashMap<String,Object>();
		map4.put("title", "街道");
		map4.put("image", R.drawable.d);
		Map<String,Object> map5=new HashMap<String,Object>();
		map5.put("title", "学堂");
		map5.put("image", R.drawable.e);
		Map<String,Object> map6=new HashMap<String,Object>();
		map6.put("title", "食堂门口");
		map6.put("image", R.drawable.f);
		Map<String,Object> map7=new HashMap<String,Object>();
		map7.put("title", "操场");
		map7.put("image", R.drawable.g);
		Map<String,Object> map8=new HashMap<String,Object>();
		map8.put("title", "走廊");
		map8.put("image", R.drawable.h);
		Map<String,Object> map9=new HashMap<String,Object>();
		map9.put("title", "庭院");
		map9.put("image", R.drawable.i);
		Map<String,Object> map10=new HashMap<String,Object>();
		map10.put("title", "草坪");
		map10.put("image", R.drawable.j);
		Map<String,Object> map11=new HashMap<String,Object>();
		map11.put("title", "街道");
		map11.put("image", R.drawable.k);
		Map<String,Object> map12=new HashMap<String,Object>();
		map12.put("title", "后院");
		map12.put("image", R.drawable.l);
		Map<String,Object> map13=new HashMap<String,Object>();
		map13.put("title", "前院");
		map13.put("image", R.drawable.m);
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		list.add(map8);
		list.add(map9);
		list.add(map10);
		list.add(map11);
		list.add(map12);
		list.add(map13);
		SimpleAdapter adapter=new SimpleAdapter(this, list, R.layout.list_item, new String[]{"title","image","text"}, new int[]{R.id.title,R.id.image});
		gv_image.setAdapter(adapter);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ib_back5:
			Intent intent5=new Intent(this,APictureShowActivity.class);
			startActivity(intent5);
			break;
		case R.id.bt_join:
			Intent intent1=new Intent(this,UserActivity.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}
}
