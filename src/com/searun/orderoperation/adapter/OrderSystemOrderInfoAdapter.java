package com.searun.orderoperation.adapter;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.entity.MaterialDto;
import com.searun.orderoperation.entity.SysOrderDto;
import com.searun.orderoperation.util.CommonUtils;

public class OrderSystemOrderInfoAdapter extends BaseAdapter {

	private Context context;
	private List<SysOrderDto> mDataList;

	public OrderSystemOrderInfoAdapter(Context context,
			List<SysOrderDto> mDataList) {
		this.context = context;
		this.mDataList = mDataList;
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
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_ordersystem_list, null);
				holder.orderNum = (TextView) convertView
						.findViewById(R.id.item_order_num);
				holder.workingNum = (TextView) convertView
						.findViewById(R.id.working_order_number);
				holder.owner = (TextView) convertView
						.findViewById(R.id.goods_owner);
				holder.from = (TextView) convertView
						.findViewById(R.id.goods_from_address);
				holder.to = (TextView) convertView
						.findViewById(R.id.goods_to_address);
				holder.earliestTime = (TextView) convertView
						.findViewById(R.id.earlist_arrival_time);
				holder.lastestTime = (TextView) convertView
						.findViewById(R.id.lastest_arrival_time);
				holder.goods_detail_layout = (LinearLayout) convertView
						.findViewById(R.id.goods_detail_layout);
				holder.item_order_detail_layout = (LinearLayout) convertView
						.findViewById(R.id.item_order_detail_layout);
				holder.expand = (CheckBox) convertView
						.findViewById(R.id.item_expand_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			SysOrderDto data = mDataList.get(position);
			String unKnow = context.getResources().getString(R.string.unknow);
			holder.orderNum.setText(String.format(context.getResources()
					.getString(R.string.order_number), TextUtils.isEmpty(data
					.getOrder_no()) ? unKnow : data.getOrder_no()));
			holder.workingNum.setText(TextUtils.isEmpty(data
					.getClh_cargo_load_no()) ? unKnow : data
					.getClh_cargo_load_no());
			holder.owner
					.setText(TextUtils.isEmpty(data.getLoh_owner_name()) ? unKnow
							: data.getLoh_owner_name());

			holder.from
					.setText(TextUtils.isEmpty(data.getLoh_load_address()) ? unKnow
							: data.getLoh_load_address());
			holder.to
					.setText(TextUtils.isEmpty(data.getLoh_unload_address()) ? unKnow
							: data.getLoh_unload_address());
			holder.earliestTime.setText(null == data
					.getLoh_expect_unload_datetime() ? unKnow : CommonUtils
					.parserData(data.getLoh_expect_unload_datetime()));
			holder.lastestTime.setText(null == data
					.getLoh_expect_unload_datetime() ? unKnow : CommonUtils
					.parserData(data.getLoh_expect_unload_datetime()));

			List<MaterialDto> materialDtos = data.getMaterialDtos();
			if (null != materialDtos) {
				for (MaterialDto materialDto : materialDtos) {
					View view = LayoutInflater.from(context).inflate(
							R.layout.item_ordersystem_goods_detail, null);
					String piece = String.format(context.getResources()
							.getString(R.string.goods_number_piece_hint),
							materialDto.getCll_consign_qty());
					((TextView) view.findViewById(R.id.goods_name))
							.setText(TextUtils.isEmpty(materialDto
									.getCll_material_name()) ? unKnow
									: materialDto.getCll_material_name()
											+ "   (" + piece + ")");
					LayoutParams params = new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					view.setPadding(30, 20, 0, 10);
					holder.item_order_detail_layout.addView(view, params);
				}
			}

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
		}

		return convertView;
	}

	private class ViewHolder {
		TextView orderNum;
		TextView workingNum;
		TextView owner;
		TextView from;
		TextView to;
		TextView earliestTime;
		TextView lastestTime;
		LinearLayout goods_detail_layout;
		LinearLayout item_order_detail_layout;
		CheckBox expand;
	}

}
