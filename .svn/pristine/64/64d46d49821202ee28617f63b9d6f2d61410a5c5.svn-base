package com.searun.orderoperation.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.adapter.HistoryOrderSystemAdapter;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.customview.ProgressAlertDialog;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.GetOrderSystemHistoryHandler;
import com.searun.orderoperation.entity.PdaPagination;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.jsonparser.OrderSystemHistoryJsonParser;
import com.searun.orderoperation.pullrefreshview.PullToRefreshBase;
import com.searun.orderoperation.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.searun.orderoperation.pullrefreshview.PullToRefreshListView;
import com.searun.orderoperation.util.CommonUtils;
import com.zzb.pubcontrols.utils.ToastUtil;

public class HistoryOrderSystemActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {
	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private Context context;

	private PullToRefreshListView history_ordersystem_content_layout;
	private List<TmDispatchDto> mDataList;
	private ListView history_ordersystem_content_listview;
	private HistoryOrderSystemAdapter mAdapter;

	/**
	 * 需求每页元素个数
	 */
	private final int pageSize = 5;
	/**
	 * 页数
	 */
	private int pageNum = 0;

	/**
	 * 尾页
	 */
	private int pageEnd = pageSize;

	/**
	 * 一共多少页
	 */
	private int totalPage = 0;

	/**
	 * 是否获取更多数据
	 */
	private boolean isGetMoreData = false;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 2000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 2001;

	private final int SHOW_TOAST = 2002;

	private ProgressAlertDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_history_order_system);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
		initAdapter();
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		getOrderSystemOrderInfo(pageNum, pageEnd);
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.history_ordersystem_title_hint);
	}

	private void initAdapter() {
		history_ordersystem_content_layout = (PullToRefreshListView) findViewById(R.id.history_ordersystem_content_layout);
		history_ordersystem_content_listview = history_ordersystem_content_layout
				.getRefreshableView();
		mDataList = new ArrayList<TmDispatchDto>();
		mAdapter = new HistoryOrderSystemAdapter(mDataList, context);
		history_ordersystem_content_listview.setAdapter(mAdapter);
		history_ordersystem_content_layout
				.setOnRefreshListener(new OnRefreshListener() {

					@Override
					public void onRefresh(int scrollState) {
						if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
							pageNum = 0;
							isGetMoreData = false;
							pageEnd = pageSize;
							getOrderSystemOrderInfo(pageNum, pageEnd);
						} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
							isGetMoreData = true;
							pageNum = pageNum + pageSize;
							pageEnd = pageNum + pageSize;
							if (pageNum < totalPage) {
								getOrderSystemOrderInfo(pageNum, pageEnd);
							} else {
								ToastUtil.show(context, "没有更多数据");
								history_ordersystem_content_layout
										.onRefreshComplete();
							}
						}
					}
				});
		history_ordersystem_content_listview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(
								HistoryOrderSystemActivity.this,
								HistoryOrderSystemDetailActivity.class);
						intent.putExtra("dataInfo", mDataList.get((int) id));
						startActivity(intent);
					}
				});
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				history_ordersystem_content_layout.onRefreshComplete();
				dismissProgress();
				break;
			case SHOW_TOAST:
				ToastUtil.show(context, msg.obj.toString());
				break;

			default:
				break;
			}
		};
	};

	private void showProgress() {
		if (progressDialog == null) {
			progressDialog = new ProgressAlertDialog(this);
		} else {
			progressDialog.show();
		}
	}

	private void dismissProgress() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	protected void getOrderSystemOrderInfo(int pageSize, int pageEnd) {
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(0);
		pagination.setAmount(pageSize);
		pagination.setEndPos(pageEnd);
		request.setPagination(pagination);
		GetOrderSystemHistoryHandler dataHandler = new GetOrderSystemHistoryHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClickListener(View view) {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);

			break;

		default:
			break;
		}
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_ORDER_SYSTEM_HISTORY_OK:
			if (!isGetMoreData) {
				doGetOrderSystemHistorySuccess(data);
			} else {
				doGetMoreOrderSystemHistorySuccess(data);
			}
			break;
		case NetWork.GET_ORDER_SYSTEM_HISTORY_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	private void doGetOrderSystemHistorySuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<TmDispatchDto>> response = OrderSystemHistoryJsonParser
					.parserOrderSystemHistoryJson(dataString);
			if (response.isSuccess()) {
				if (null == response || null == response.getData()) {
					ToastUtil.show(context, "获取作业信息失败，请重新获取");
					return;
				}
				totalPage = new Long(response.getTotal()).intValue();
				mDataList = response.getData();
				mAdapter = new HistoryOrderSystemAdapter(mDataList, context);
				history_ordersystem_content_listview.setAdapter(mAdapter);
				showView(mDataList);
			} else {
				String result = response.getMessage();
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = result;
				msg.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doGetMoreOrderSystemHistorySuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<TmDispatchDto>> response = OrderSystemHistoryJsonParser
					.parserOrderSystemHistoryJson(dataString);
			if (response.isSuccess()) {
				if (null == response || null == response.getData()) {
					ToastUtil.show(context, "获取作业信息失败，请重新获取");
					return;
				}
				totalPage = new Long(response.getTotal()).intValue();
				mDataList = response.getData();
				showView(mDataList);
			} else {
				String result = response.getMessage();
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = result;
				msg.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showView(List<TmDispatchDto> dataList) {
		if (null == dataList)
			return;
		mAdapter = new HistoryOrderSystemAdapter(dataList, context);
		history_ordersystem_content_listview.setAdapter(mAdapter);
	}

}
