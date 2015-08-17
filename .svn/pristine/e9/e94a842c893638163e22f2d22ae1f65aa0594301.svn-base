package com.searun.orderoperation.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.application.ConstantPool;
import com.searun.orderoperation.entity.SysOrderDto;
import com.searun.orderoperation.util.CommonUtils;

public class OrderSystemOrderInfoAdapter2 extends BaseAdapter {

	private Context context;
	private List<SysOrderDto> mDataList;
	private Handler myHandler;

	public OrderSystemOrderInfoAdapter2(Context context,
			List<SysOrderDto> mDataList, Handler myHandler) {
		this.context = context;
		this.mDataList = mDataList;
		this.myHandler = myHandler;
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_ordersystem_list2, null);
				holder.orderNum = (TextView) convertView
						.findViewById(R.id.item_order_num);
				holder.to = (TextView) convertView
						.findViewById(R.id.goods_from_address);
				holder.lastestTime = (TextView) convertView
						.findViewById(R.id.lastest_arrival_time);
				holder.item_order_detail_layout = (LinearLayout) convertView
						.findViewById(R.id.item_order_detail_layout);
				holder.expand = (CheckBox) convertView
						.findViewById(R.id.item_expand_btn);
				holder.arrival = (Button) convertView
						.findViewById(R.id.arrival_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final SysOrderDto data = mDataList.get(position);
			String unKnow = context.getResources().getString(R.string.unknow);
			holder.orderNum.setText(String.format(context.getResources()
					.getString(R.string.order_number), TextUtils.isEmpty(data
					.getOrder_no()) ? unKnow : data.getOrder_no()));
			holder.to
					.setText(TextUtils.isEmpty(data.getLoh_unload_address()) ? unKnow
							: data.getLoh_unload_address());
			holder.lastestTime.setText(null == data
					.getLoh_expect_unload_datetime() ? unKnow : CommonUtils
					.parserData(data.getLoh_expect_unload_datetime()));
			holder.expand
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							holder.item_order_detail_layout
									.setVisibility(isChecked ? View.VISIBLE
											: View.GONE);
						}
					});
			// holder.arrival.setVisibility(data.isArrival() ? View.INVISIBLE
			// : View.VISIBLE);
			holder.arrival.setText(data.isArrival() ? "已到货" : "到货");
			holder.arrival.setEnabled(data.isArrival() ? false : true);
			holder.arrival.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = myHandler.obtainMessage();
					msg.what = ConstantPool.TAG_ARRIVAL_GOODS;
					msg.arg1 = position;
					msg.obj = data.getClh_cargo_load_no();
					msg.sendToTarget();
				}
			});
		}

		return convertView;
	}

	private class ViewHolder {
		TextView orderNum;
		TextView to;
		TextView lastestTime;
		Button arrival;
		LinearLayout item_order_detail_layout;
		CheckBox expand;
	}

}
