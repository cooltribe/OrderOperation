package com.searun.orderoperation.activity;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.searun.orderoperation.R;
import com.searun.orderoperation.adapter.OrderSystemOrderInfoAdapter;
import com.searun.orderoperation.adapter.OrderSystemOrderInfoAdapter2;
import com.searun.orderoperation.application.ConstantPool;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.customview.ProgressAlertDialog;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.ArrivalGoodsHandler;
import com.searun.orderoperation.datahandler.GetOrderSystemOrderInfoHandler;
import com.searun.orderoperation.entity.PdaPagination;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SysOrderDto;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.entity.VehicleStatusDto;
import com.searun.orderoperation.jsonparser.OrderSystemOrderInfoJsonParser;
import com.searun.orderoperation.jsonparser.ResultCodeJsonParser;
import com.searun.orderoperation.pullrefreshview.PullToRefreshBase;
import com.searun.orderoperation.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.searun.orderoperation.pullrefreshview.PullToRefreshListView;
import com.searun.orderoperation.util.CommonUtils;
import com.zzb.pubcontrols.utils.ToastUtil;

/**
 * 到货操作
 * 
 * @author zhazhaobao
 * 
 */
public class ArrivalGoodsOperationActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 调度单号
	 */
	private TextView dispatchNum;

	/**
	 * 车牌
	 */
	private TextView vehicle_number;

	/**
	 * 车型
	 */
	private TextView vehicle_type;

	/**
	 * 总件数
	 */
	private TextView total_package;

	/**
	 * 总体积
	 */
	private TextView total_volume;

	/**
	 * 总净重
	 */
	private TextView total_weight;

	/**
	 * 订单 refreshview
	 */
	private PullToRefreshListView ordersystem_content_layout;
	// private ListView ordersystem_content_layout;
	private ListView ordersystem_content_listview;
	private OrderSystemOrderInfoAdapter2 mAdapter;

	private Context context;

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
	 * APP 初次进入由于数据是直接获得，所以添加此FLAG，获取更多的时候执行判断
	 */
	private boolean isFirstRefreshData = false;

	private TmDispatchDto tmDispatchDto;

	private List<SysOrderDto> mDataList;

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

	private SharedPreferences sPreferences;

	private boolean isAllGoodsArrival = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_arrival_goods_operation);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		tmDispatchDto = (TmDispatchDto) getIntent().getSerializableExtra(
				"tmDispatchDto");
		sPreferences = getSharedPreferences(ConstantPool.OPERATION_PREFERENCES,
				Context.MODE_PRIVATE);
		CommonUtils.addActivity(this);
		initView();
		initAdapter();
		showView(tmDispatchDto);
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.arrival_goods_operation_hint);

		dispatchNum = (TextView) findViewById(R.id.dispatchNum);
		vehicle_number = (TextView) findViewById(R.id.vehicle_number);
		vehicle_type = (TextView) findViewById(R.id.vehicle_type);
		total_package = (TextView) findViewById(R.id.total_package);
		total_volume = (TextView) findViewById(R.id.total_volume);
		total_weight = (TextView) findViewById(R.id.total_weight);
	}

	private void showView(TmDispatchDto tmDispatchDto) {
		if (null == tmDispatchDto)
			return;
		String unknow = getResources().getString(R.string.unknow);
		dispatchNum.setText(String
				.format(context.getResources().getString(
						R.string.dispatch_number_hint), TextUtils
						.isEmpty(tmDispatchDto.getDh_dispatch_no()) ? unknow
						: tmDispatchDto.getDh_dispatch_no()));
		vehicle_number.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_truck_license_no()) ? unknow : tmDispatchDto
				.getDh_truck_license_no());
		vehicle_type.setText(TextUtils.isEmpty(tmDispatchDto.getCx()) ? unknow
				: tmDispatchDto.getCx());
		total_package.setText(String.format(
				getResources().getString(R.string.goods_number_piece_hint),
				tmDispatchDto.getLoh_total_packs()));
		total_volume.setText(String.format(
				getResources().getString(R.string.stere_hint),
				null == tmDispatchDto.getLoh_total_cubage() ? unknow
						: tmDispatchDto.getLoh_total_cubage().toString()));
		total_weight
				.setText(String
						.format(getResources().getString(R.string.kg_hint),
								null == tmDispatchDto
										.getLoh_total_gross_weight() ? unknow
										: tmDispatchDto
												.getLoh_total_gross_weight()
												.toString()));
		mDataList = tmDispatchDto.getSysOrderDtos();
		mAdapter = new OrderSystemOrderInfoAdapter2(context, mDataList,
				myHandler);
		pageNum = mDataList.size();
		pageEnd = pageNum + pageSize;
		ordersystem_content_listview.setAdapter(mAdapter);
		// ordersystem_content_layout.setAdapter(mAdapter);
	}

	private int currentGoodsNumber = -1;

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ConstantPool.TAG_ARRIVAL_GOODS:
				doArrivalGoods(msg);
				break;
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				dismissProgress();
				break;
			case SHOW_TOAST:
				isAllGoodsArrival = false;
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

	private void initAdapter() {
		ordersystem_content_layout = (PullToRefreshListView) findViewById(R.id.ordersystem_content_layout);
		// ordersystem_content_layout = (ListView)
		// findViewById(R.id.ordersystem_content_layout);
		ordersystem_content_listview = ordersystem_content_layout
				.getRefreshableView();
		// mDataList = new ArrayList<SysOrderDto>();
		// mAdapter = new OrderSystemOrderInfoAdapter(context, mDataList);
		ordersystem_content_listview.setAdapter(mAdapter);
		// ordersystem_content_layout.setAdapter(mAdapter);
		ordersystem_content_layout
				.setOnRefreshListener(new OnRefreshListener() {

					@Override
					public void onRefresh(int scrollState) {
						if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
							pageNum = 0;
							isGetMoreData = false;
							isFirstRefreshData = false;
							pageEnd = pageSize;
							getOrderSystemOrderInfo(pageNum, pageEnd,
									tmDispatchDto);
						} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
							if (totalPage == 0) {
								isFirstRefreshData = true;
								getOrderSystemOrderInfo(pageNum, pageEnd,
										tmDispatchDto);
							} else if (pageNum < totalPage) {
								isGetMoreData = true;
								isFirstRefreshData = false;
								pageNum = pageNum + pageSize;
								pageEnd = pageNum + pageSize;
								getOrderSystemOrderInfo(pageNum, pageEnd,
										tmDispatchDto);
							} else {
								ToastUtil.show(context, "没有更多数据");
								ordersystem_content_layout.onRefreshComplete();
							}
						}
					}
				});
	}

	/**
	 * 获取订单信息
	 * 
	 * @param pageNum
	 */
	protected void getOrderSystemOrderInfo(int pageNum, int pageEnd,
			TmDispatchDto tmDispatchDto) {
		PdaRequest<TmDispatchDto> request = new PdaRequest<TmDispatchDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(pageNum);
		pagination.setAmount(pageSize);
		pagination.setEndPos(pageEnd);
		request.setData(tmDispatchDto);
		request.setPagination(pagination);
		GetOrderSystemOrderInfoHandler dataHandler = new GetOrderSystemOrderInfoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	protected void doArrivalGoods(Message msg) {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		currentGoodsNumber = msg.arg1;
		PdaRequest<VehicleStatusDto> request = new PdaRequest<VehicleStatusDto>();
		VehicleStatusDto vehicleStatusDto = new VehicleStatusDto();
		vehicleStatusDto.setVehicle_num(sPreferences.getString("vehicleNum", ""));
		vehicleStatusDto.setLat(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("latitude", "0"))));
		vehicleStatusDto.setLng(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("longitude", "0"))));
		vehicleStatusDto
				.setLocation_desc(sPreferences.getString("address", ""));
		vehicleStatusDto.setMobile(sPreferences.getString("phone", ""));
		vehicleStatusDto.setTask_status(4);
		vehicleStatusDto.setTask_type(sPreferences.getInt("vehicleType", 0));
		vehicleStatusDto.setTask_no(msg.obj.toString());
		vehicleStatusDto.setDispatch_id(tmDispatchDto.getDispatchId());
		vehicleStatusDto.setDh_dispatch_no(tmDispatchDto.getDh_dispatch_no());
		request.setData(vehicleStatusDto);
		ArrivalGoodsHandler dataHandler = new ArrivalGoodsHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.upload_btn2:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doAllGoodsArrival();
				}
			}).start();
			break;

		default:
			break;
		}
	}

	/**
	 * 全部到货
	 */
	private void doAllGoodsArrival() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		isAllGoodsArrival = true;
		PdaRequest<VehicleStatusDto> request = new PdaRequest<VehicleStatusDto>();
		VehicleStatusDto vehicleStatusDto = new VehicleStatusDto();
		vehicleStatusDto.setVehicle_num(sPreferences.getString("vehicleNum", ""));
		vehicleStatusDto.setLat(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("latitude", "0"))));
		vehicleStatusDto.setLng(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("longitude", "0"))));
		vehicleStatusDto
				.setLocation_desc(sPreferences.getString("address", ""));
		vehicleStatusDto.setMobile(sPreferences.getString("phone", ""));
		vehicleStatusDto.setTask_status(4);
		vehicleStatusDto.setTask_type(sPreferences.getInt("vehicleType", 0));
		vehicleStatusDto.setDispatch_id(tmDispatchDto.getDispatchId());
		vehicleStatusDto.setDh_dispatch_no(tmDispatchDto.getDh_dispatch_no());
		request.setData(vehicleStatusDto);
		ArrivalGoodsHandler dataHandler = new ArrivalGoodsHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
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
		case NetWork.ARRIVAL_GOODS_OK:
			doArrivalGoodsSuccess(data);
			break;
		case NetWork.GET_ORDER_SYSTEM_ORDER_INFO_OK:
			if (!isFirstRefreshData || !isGetMoreData) {
				doGetOrderSystemInfoSuccess(data);
			} else {
				doGetMoreOrderSystemInfoSuccess(data);
			}
			break;
		case NetWork.GET_ORDER_SYSTEM_ORDER_INFO_ERROR:
		case NetWork.ARRIVAL_GOODS_ERROR:
			isAllGoodsArrival = false;
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	private void doGetMoreOrderSystemInfoSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<SysOrderDto>> response = OrderSystemOrderInfoJsonParser
					.parserOrderSystemOrderInfoJson(dataString);
			if (response.isSuccess()) {
				if (null == response || null == response.getData()) {
					ToastUtil.show(context, "获取订单信息失败，请重新获取");
					return;
				}
				totalPage = new Long(response.getTotal()).intValue();
				for (SysOrderDto sysOrderDto : response.getData()) {
					mDataList.add(sysOrderDto);
				}
				mAdapter.notifyDataSetChanged();
				ordersystem_content_layout.onRefreshComplete();

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

	/**
	 * 获取作业信息成功
	 * 
	 * @param data
	 */
	private void doGetOrderSystemInfoSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<SysOrderDto>> response = OrderSystemOrderInfoJsonParser
					.parserOrderSystemOrderInfoJson(dataString);
			if (response.isSuccess()) {
				// mDataList = response.getData();
				// myHandler.sendEmptyMessage(GET_ORDER_INFO_SUCCESS);

				if (null == response || null == response.getData()) {
					ToastUtil.show(context, "获取订单信息失败，请重新获取");
					return;
				}
				if (!isGetMoreData && !isFirstRefreshData) {
					mDataList.clear();
				}
				for (SysOrderDto sysOrderDto : response.getData()) {
					mDataList.add(sysOrderDto);
				}
				totalPage = new Long(response.getTotal()).intValue();
				mAdapter = new OrderSystemOrderInfoAdapter2(context, mDataList,
						myHandler);
				ordersystem_content_listview.setAdapter(mAdapter);
				ordersystem_content_layout.onRefreshComplete();
				// ordersystem_content_layout.setAdapter(mAdapter);

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

	private void doArrivalGoodsSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		try {
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (null == response) {
				isAllGoodsArrival = false;
				ToastUtil.show(context, "到货操作失败，请重新操作");
				return;
			}
			if (response.isSuccess()) {
				// 全部到货
				if (isAllGoodsArrival) {
					ToastUtil.show(context, "货物已全部到货");
					Intent intent = new Intent(this,MainActivity.class);
					startActivity(intent);
					CommonUtils.finishAllActivity();
				} else {
					doSingleGoodsArrival();
				}
			} else {
				result = response.getMessage();
				msg.obj = result;
				msg.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = getResources().getString(R.string.network_error_hint);
			msg.obj = result;
			msg.sendToTarget();
		}
	}

	/**
	 * 单个货物到货操作
	 */
	private void doSingleGoodsArrival() {
		ToastUtil.show(context, "提交成功");
		mDataList.get(currentGoodsNumber).setArrival(true);
		mAdapter.notifyDataSetChanged();
	}

}
