package com.example.cw_9_7;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.FileEntity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
	private ListView lv;
	private List<FileInfo> list;
	private MyAdapter adapter;
	private String filep;
	private String pathlast;
	private String pathexit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pathexit=rootPath.substring(0,rootPath.lastIndexOf("/"));
		lv = (ListView) findViewById(R.id.lv);
		list = new ArrayList<FileInfo>();
		adapter = new MyAdapter();
		lv.setAdapter(adapter);
		pathlast=rootPath;
		listFile(rootPath);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FileInfo info = list.get(position);
				if (info.getType() == 0) { 
					pathlast=info.getPath();
					listFile(pathlast);
				} else{ 
					
				}
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		String path=pathlast.substring(0, pathlast.lastIndexOf("/"));
		if(path.equals(pathexit)){
			finish();
		}else {
			
			pathlast=path;
			listFile(path);
		}
		
		
		
	}
	private void listFile(String path) {
		list.clear();
		
		
		File file = new File(path);
		
		File[] fs = file.listFiles();
		for (File file2 : fs) {
			String name = file2.getName();
			String p = file2.getAbsolutePath();
			if (file2.isDirectory()) { 
				list.add(new FileInfo(name, 0, p));
			} else {
				if (name.endsWith(".mp3")) {
					list.add(new FileInfo(name, 1, p));
				} else if (name.endsWith(".txt")) {
					list.add(new FileInfo(name, 2, p));
				} else if (name.endsWith(".apk")) {
					list.add(new FileInfo(name, 3, p));
				} else if (name.endsWith(".xml")) {
					list.add(new FileInfo(name, 4, p));
				} else {
					list.add(new FileInfo(name, 5, p));
				}
			}
		}
	
		adapter.notifyDataSetChanged();
	}
	
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = null;
			if (view == null) {
				view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list, parent, false);
				holder = new ViewHolder(view);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			FileInfo info = list.get(position);
			holder.tv.setText(info.getName());
			int type = info.getType();
			int resId = 0;
			switch (type) {
			case 0:
				resId = R.drawable.file;
				break;
				
			case 1:
				resId = R.drawable.mps;
				break;
				
			case 2:
				resId = R.drawable.txt;
				break;
				
			case 3:
				resId = R.drawable.apk;
				break;
				
			case 4:
				resId = R.drawable.d;
				break;

			default:
			
				break;
			}
			holder.img.setImageResource(resId);
			return view;
		}
	}
	

	class ViewHolder {
		ImageView img;
		TextView tv;
		
		public ViewHolder(View view) {
			img = (ImageView) view.findViewById(R.id.img);
			tv = (TextView) view.findViewById(R.id.tv);
		}
	}
}