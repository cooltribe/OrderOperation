package com.searun.orderoperation.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.adapter.OrderSystemOrderInfoAdapter;
import com.searun.orderoperation.application.ConstantPool;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.customview.ImageAlertDlialog;
import com.searun.orderoperation.customview.ProgressAlertDialog;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.GetAttachmentsHandler;
import com.searun.orderoperation.datahandler.GetOrderSystemCurrentInfoHandler;
import com.searun.orderoperation.datahandler.GetOrderSystemHistoryHandler;
import com.searun.orderoperation.datahandler.GetOrderSystemInfoHandler;
import com.searun.orderoperation.datahandler.GetOrderSystemOrderInfoHandler;
import com.searun.orderoperation.entity.ImageDto;
import com.searun.orderoperation.entity.PdaPagination;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SCM_AttachMentDto;
import com.searun.orderoperation.entity.SysOrderDto;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.jsonparser.AttachmentsJsonParser;
import com.searun.orderoperation.jsonparser.OrderSystemInfoJsonParser;
import com.searun.orderoperation.jsonparser.OrderSystemOrderInfoJsonParser;
import com.searun.orderoperation.pullrefreshview.PullToRefreshBase;
import com.searun.orderoperation.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.searun.orderoperation.pullrefreshview.PullToRefreshListView;
import com.searun.orderoperation.util.CommonUtils;
import com.searun.orderoperation.util.ToastUtil;
import com.zzb.pubcontrols.controls.RemoteImageView;

/**
 * 历史执行作业详情
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("NewApi")
public class HistoryOrderSystemDetailActivity extends BaseActivity implements
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
	 * 订单 refreshview
	 */
	private PullToRefreshListView ordersystem_content_layout;
	private ListView ordersystem_content_listview;

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
	 * 主驾驶
	 */
	private TextView main_driver;

	/**
	 * 主驾驶手机号码
	 */
	private TextView main_driver_phone;

	/**
	 * 副驾驶
	 */
	private TextView copilot_name;

	/**
	 * 副驾驶手机号码
	 */
	private TextView copilot_phone;

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

	private Context context;

	private List<SysOrderDto> mDataList;

	private OrderSystemOrderInfoAdapter mAdapter;

	/**
	 * 调度单
	 */
	private TmDispatchDto tmDispatchDto;

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

	/**
	 * 定位
	 */
	private Button tailafter_btn;
	/**
	 * 上传
	 */
	private Button upload_btn;

	/**
	 * 确认上传
	 */
	private Button ensure_upload_btn;

	private Button check_photo_btn;

	/**
	 * 管理回单布局
	 */
	private LinearLayout ordersystem_photo_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_history_order_system_detail);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		tmDispatchDto = (TmDispatchDto) getIntent().getSerializableExtra(
				"dataInfo");
		initView();
		initAdapter();
		// showView(tmDispatchDto);
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		getOrderInfo();
		doGetAttachments();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.history_ordersystem_title_hint);

		dispatchNum = (TextView) findViewById(R.id.dispatchNum);
		vehicle_number = (TextView) findViewById(R.id.vehicle_number);
		vehicle_type = (TextView) findViewById(R.id.vehicle_type);
		total_package = (TextView) findViewById(R.id.total_package);
		total_volume = (TextView) findViewById(R.id.total_volume);
		total_weight = (TextView) findViewById(R.id.total_weight);
		main_driver = (TextView) findViewById(R.id.main_driver_name);
		main_driver_phone = (TextView) findViewById(R.id.main_driver_phone);
		copilot_name = (TextView) findViewById(R.id.copilot_name);
		copilot_phone = (TextView) findViewById(R.id.copilot_phone);

		tailafter_btn = (Button) findViewById(R.id.tailafter_btn);
		tailafter_btn.setVisibility(View.GONE);
		upload_btn = (Button) findViewById(R.id.upload_btn);
		upload_btn.setVisibility(View.GONE);
		ensure_upload_btn = (Button) findViewById(R.id.ensure_upload_btn);
		ensure_upload_btn.setVisibility(View.GONE);

		ordersystem_photo_layout = (LinearLayout) findViewById(R.id.ordersystem_photo_layout);
		int size = tmDispatchDto.getAttatchMentDtos().size();
		for (int i = 0; i < size; i++) {
			ImageDto photo = tmDispatchDto.getAttatchMentDtos().get(i)
					.getPath();
			addPhotoIntoLayout(context, photo.getHeaderImgURL());
		}
		check_photo_btn = (Button) findViewById(R.id.check_photo_btn);
		check_photo_btn.setVisibility(View.VISIBLE);
	}

	private void initAdapter() {
		ordersystem_content_layout = (PullToRefreshListView) findViewById(R.id.ordersystem_content_layout);
		ordersystem_content_listview = ordersystem_content_layout
				.getRefreshableView();
		mDataList = new ArrayList<SysOrderDto>();
		mAdapter = new OrderSystemOrderInfoAdapter(context, mDataList);
		ordersystem_content_listview.setAdapter(mAdapter);
		ordersystem_content_layout
				.setOnRefreshListener(new OnRefreshListener() {

					@Override
					public void onRefresh(int scrollState) {
						if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
							pageNum = 0;
							pageEnd = pageSize;
							isGetMoreData = false;
							isFirstRefreshData = false;
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
	 * 获取照片信息
	 */
	private void doGetAttachments() {
		PdaRequest<SCM_AttachMentDto> request = new PdaRequest<SCM_AttachMentDto>();
		SCM_AttachMentDto data = new SCM_AttachMentDto();
		data.setPbillid(tmDispatchDto.getDispatchId());
		data.setTblName("Dispatch");
		request.setData(data);
		GetAttachmentsHandler dataHandler = new GetAttachmentsHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 获取指定调度单号的调度信息
	 */
	private void getOrderInfo() {
		PdaRequest<TmDispatchDto> request = new PdaRequest<TmDispatchDto>();
		request.setData(tmDispatchDto);
		GetOrderSystemCurrentInfoHandler dataHandler = new GetOrderSystemCurrentInfoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();

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

	/**
	 * 获取作业信息
	 * 
	 */
	protected void getOrderSystemInfo() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		GetOrderSystemInfoHandler dataHandler = new GetOrderSystemInfoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
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
		request.setData(tmDispatchDto);
		pagination.setStartPos(pageNum);
		pagination.setAmount(pageSize);
		pagination.setEndPos(pageEnd);
		request.setPagination(pagination);
		GetOrderSystemOrderInfoHandler dataHandler = new GetOrderSystemOrderInfoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.check_photo_btn:
			Intent photoIntent = new Intent(this, UploadOrderActivity.class);
			photoIntent.putExtra("tmDispatchDto", tmDispatchDto);
			photoIntent.putExtra("isCheckPhoto", true);
			startActivity(photoIntent);
			break;

		default:
			break;
		}

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

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ordersystem_content_layout.onRefreshComplete();
			switch (msg.what) {
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
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
		main_driver.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_primary_driver()) ? unknow : tmDispatchDto
				.getDh_primary_driver());
		main_driver_phone.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_primary_tel()) ? unknow : tmDispatchDto
				.getDh_primary_tel());
		copilot_name.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_secondary_driver()) ? "无" : tmDispatchDto
				.getDh_secondary_driver());
		copilot_phone.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_secondary_tel()) ? "无" : tmDispatchDto
				.getDh_secondary_tel());
		mDataList = tmDispatchDto.getSysOrderDtos();
		mAdapter = new OrderSystemOrderInfoAdapter(context, mDataList);
		pageNum = mDataList.size();
		pageEnd = pageNum + pageSize;
		ordersystem_content_listview.setAdapter(mAdapter);
		// ordersystem_content_layout.setAdapter(mAdapter);
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_ORDER_SYSTEM_INFO_OK:
			doGetOrderSystemInfoSuccess(data);
			break;
		case NetWork.GET_ORDER_SYSTEM_ORDER_INFO_OK:
			if (!isFirstRefreshData || !isGetMoreData) {
				doGetOrderSystemInfoSuccess(data);
			} else {
				doGetMoreOrderSystemInfoSuccess(data);
			}
			break;
		// case NetWork.GET_ORDER_SYSTEM_HISTORY_OK:
		// doGetOrderSystemHistorySuccess(data);
		// break;
		case NetWork.GET_ORDER_SYSTEM_CURRENT_INFO_OK:
			doGetOrderSystemOrderInfoSuccess(data);
			break;
		case NetWork.GET_ATTACHMENTS_OK:
			doGetAttachmentsSuccess(data);
			break;
		case NetWork.GET_ATTACHMENTS_ERROR:
		case NetWork.GET_ORDER_SYSTEM_CURRENT_INFO_ERROR:
		case NetWork.GET_ORDER_SYSTEM_HISTORY_ERROR:
		case NetWork.GET_ORDER_SYSTEM_INFO_ERROR:
		case NetWork.GET_ORDER_SYSTEM_ORDER_INFO_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		default:
			break;
		}
	}

	/**
	 * 获取订单图片成功
	 * 
	 * @param data
	 */
	private void doGetAttachmentsSuccess(Object data) {
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
			PdaResponse<List<SCM_AttachMentDto>> response = AttachmentsJsonParser
					.parserAttachmentsJson(dataString);
			if (null == response) {
				ToastUtil.show(context, "获取图片信息失败，请重新获取");
				return;
			}
			if (response.isSuccess()) {
				List<SCM_AttachMentDto> resultData = response.getData();
				for (final SCM_AttachMentDto dto : resultData) {
					View view = LayoutInflater.from(context).inflate(
							R.layout.upload_photo_layout, null);
					RemoteImageView image = (RemoteImageView) view
							.findViewById(R.id.upload_photo_img);
					ImageView delete = (ImageView) view
							.findViewById(R.id.upload_photo_delete);
					delete.setVisibility(View.GONE);
					image.draw(dto.getPath().getHeaderImgURL(),
							ConstantPool.DEFAULT_ICON_PATH, false, false);
					image.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// 显示回单
							doShowPeceipt(dto.getPath().getHeaderImgURL());
						}
					});
					delete.setTag(dto.getPath().getHeaderImgURL());
					ordersystem_photo_layout.addView(view);
					tmDispatchDto.getAttatchMentDtos().add(dto);
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
	 * 显示回单
	 */
	protected void doShowPeceipt(String imagePath) {
		ImageAlertDlialog dialog = new ImageAlertDlialog(this, imagePath, "");
		dialog.show();
	}

	// private void doGetOrderSystemHistorySuccess(Object data) {
	// String dataString = null;
	// try {
	// dataString = new String((byte[]) data, "UTF-8");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// try {
	// PdaResponse<List<TmDispatchDto>> response = OrderSystemHistoryJsonParser
	// .parserOrderSystemHistoryJson(dataString);
	// if (response.isSuccess()) {
	// if (null == response || null == response.getData()) {
	// ToastUtil.show(context, "获取作业信息失败，请重新获取");
	// return;
	// }
	// totalPage = new Long(response.getTotal()).intValue();
	// mDataList = response.getData();
	// showView(tmDispatchDto);
	// } else {
	// String result = response.getMessage();
	// Message msg = myHandler.obtainMessage();
	// msg.what = SHOW_TOAST;
	// msg.obj = result;
	// msg.sendToTarget();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 获取订单信息成功
	 * 
	 * @param data
	 */
	private void doGetOrderSystemOrderInfoSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<TmDispatchDto> response = OrderSystemInfoJsonParser
					.parserOrderSystemInfoJson(dataString);
			if (response.isSuccess()) {
				if (null == response || null == response.getData()) {
					ToastUtil.show(context, "获取作业信息失败，请重新获取");
					return;
				}
				tmDispatchDto = response.getData();
				showView(tmDispatchDto);
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
				mAdapter = new OrderSystemOrderInfoAdapter(context, mDataList);
				ordersystem_content_listview.setAdapter(mAdapter);
				ordersystem_content_layout.onRefreshComplete();

			} else {
				String result = response.getMessage();
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = result;
				myHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	private void addPhotoIntoLayout(Context context, String imgURL) {
		if (TextUtils.isEmpty(imgURL))
			return;
		RemoteImageView view = new RemoteImageView(context);
		view.draw(imgURL, ConstantPool.DEFAULT_ICON_PATH, false, false);
		LayoutParams params = new LayoutParams(200, 200);
		view.setPadding(30, 30, 30, 30);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		ordersystem_photo_layout.addView(view, params);
	}
}
