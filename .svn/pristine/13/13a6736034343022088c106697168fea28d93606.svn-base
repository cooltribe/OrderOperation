package com.searun.orderoperation.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.entity.TmDispatchDto;

public class HistoryOrderSystemAdapter extends BaseAdapter {

	private List<TmDispatchDto> mDataList;

	private Context context;

	public HistoryOrderSystemAdapter(List<TmDispatchDto> mDataList,
			Context context) {
		this.mDataList = mDataList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_history_order_system, null);
				holder.normal_text = (TextView) convertView
						.findViewById(R.id.item_dispatchNum);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			TmDispatchDto tmDispatchDto = mDataList.get(position);
			holder.normal_text
					.setText(String.format(
							context.getResources().getString(
									R.string.dispatch_number_hint),
							TextUtils.isEmpty(tmDispatchDto.getDh_dispatch_no()) ? context
									.getResources().getString(R.string.unknow)
									: tmDispatchDto.getDh_dispatch_no()));
		}

		return convertView;
	}

	final class ViewHolder {
		TextView normal_text;
	}

}
