# App_Start
干净纯洁的游戏模板



青山绿水今犹在 不见当年少年郎 
 
可怜痴情错付了 世上从此无良人


1. 解决文字和drawableleft和文字一起时不居中
 
	    public class MDMRadioButton extends  RadioButton {
            public MDMRadioButton(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
            }

            public MDMRadioButton(Context context, AttributeSet attrs) {
                super(context, attrs);
            }

            public MDMRadioButton(Context context) {
                super(context);
            }

            @Override
            protected void onDraw(Canvas canvas) {
                    //获取设置的图片
                Drawable[] drawables = getCompoundDrawables();
                if (drawables != null) {
                    //第一个是left
                    Drawable drawableLeft = drawables[0];
                    if (drawableLeft != null) {
                        //获取文字的宽度
                        float textWidth = getPaint().measureText(getText().toString());
                        int drawablePadding = getCompoundDrawablePadding();
                        int drawableWidth = 0;
                        drawableWidth = drawableLeft.getIntrinsicWidth();
                        float bodyWidth = textWidth + drawableWidth + drawablePadding;
                        int y = getWidth();
                        canvas.translate((getWidth() - bodyWidth) / 2, 0);
                    }
                }
                super.onDraw(canvas);
            }
	    }

2. 带下划线的导航栏，使用radioButton实现，使用selector和shape

3. Picasso切成圆角矩形图片

            Picasso.with(context).load(path.toString()).transform(new CircleTransformation(getContext())).into(imageView);

            public class CircleTransformation implements Transformation {
                Context context;
                int mRaduis;
                public CircleTransformation(Context context) {
                    this.context = context;
                }
                public CircleTransformation(Context context, int mRaduis) {
                    this.context = context;
                    this.mRaduis = mRaduis;
                }
                @Override
                public Bitmap transform(Bitmap source) {
                    int raduis = 10;
                    Bitmap output = BitmapFillet.fillet(source, DimenUtils.dp2px(context, mRaduis == 0 ? raduis : mRaduis), BitmapFillet.CORNER_ALL);
                    if (source != null) {
                        source.recycle();
                    }
                    return output;
                }
                @Override
                public String key() {
                    return "roundcorner";
                }
                public static class BitmapFillet {
                    public static final int CORNER_NONE = 0;
                    public static final int CORNER_TOP_LEFT = 1;
                    public static final int CORNER_TOP_RIGHT = 1 << 1;
                    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
                    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
                    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
                    public static final int CORNER_TOP = CORNER_TOP_LEFT | CORNER_TOP_RIGHT;
                    public static final int CORNER_BOTTOM = CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
                    public static final int CORNER_LEFT = CORNER_TOP_LEFT | CORNER_BOTTOM_LEFT;
                    public static final int CORNER_RIGHT = CORNER_TOP_RIGHT | CORNER_BOTTOM_RIGHT;
                    public static Bitmap fillet(Bitmap bitmap, int roundPx, int corners) {
                        try {
            // 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
            // 然后在画板上画出一个想要的形状的区域。
            // 最后把源图片帖上。
                            final int width = bitmap.getWidth();
                            final int height = bitmap.getHeight();
                            Bitmap paintingBoard = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(paintingBoard);
                            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
                            final Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            paint.setColor(Color.BLACK);
            //画出4个圆角
                            final RectF rectF = new RectF(0, 0, width, height);
                            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            //把不需要的圆角去掉
                            int notRoundedCorners = corners ^ CORNER_ALL;
                            if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
                                clipTopLeft(canvas, paint, roundPx, width, height);
                            }
                            if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
                                clipTopRight(canvas, paint, roundPx, width, height);
                            }
                            if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
                                clipBottomLeft(canvas, paint, roundPx, width, height);
                            }
                            if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
                                clipBottomRight(canvas, paint, roundPx, width, height);
                            }
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            //帖子图
                            final Rect src = new Rect(0, 0, width, height);
                            final Rect dst = src;
                            canvas.drawBitmap(bitmap, src, dst, paint);
                            return paintingBoard;
                        } catch (Exception exp) {
                            return bitmap;
                        }
                    }
                    private static void clipTopLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
                        final Rect block = new Rect(0, 0, offset, offset);
                        canvas.drawRect(block, paint);
                    }
                    private static void clipTopRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
                        final Rect block = new Rect(width - offset, 0, width, offset);
                        canvas.drawRect(block, paint);
                    }
                    private static void clipBottomLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
                        final Rect block = new Rect(0, height - offset, offset, height);
                        canvas.drawRect(block, paint);
                    }
                    private static void clipBottomRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
                        final Rect block = new Rect(width - offset, height - offset, width, height);
                        canvas.drawRect(block, paint);
                    }
                }
        }

4. 使用recyclerview + smartRefresh库完成ExpandableListView二级菜单的功能 

5. 使用flatMap进行类似注册之后再登录的二次http请求操作

        mHttpApi.getFirstData()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<FirstData>() {
                    @Override
                    public void accept(FirstData data) throws Exception {
                        firstData = data;
                    }
                })
                .flatMap(new Function<FirstData, ObservableSource<FirstGameListData>>() {
                    @Override
                    public ObservableSource<FirstGameListData> apply(FirstData data) throws Exception {
                        return mHttpApi.getGameListData(page, 10)
                                .subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BObserver<FirstGameListData, CompositeDisposable>(mCompositeDisposable) {
                    @Override
                    public void onNext(FirstGameListData firstGameListData) {
                        view.refreshData(firstData, firstGameListData);
                    }
                });
                
6.okhttp添加公共参数(https://ysy950803.blog.csdn.net/article/details/94778870?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-94778870-blog-78643677.pc_relevant_multi_platform_whitelistv1&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-94778870-blog-78643677.pc_relevant_multi_platform_whitelistv1&utm_relevant_index=1)

	.addInterceptor(new Interceptor() {
		@NotNull
		@Override
		public Response intercept(@NotNull Chain chain) throws IOException {
			Request request = chain.request();
			Request.Builder requestBuilder = request.newBuilder();
			HttpUrl.Builder 7  = request.url().newBuilder();
			httpUrlBuilder.addQueryParameter("guid", LoginUtils.getInstance().getUser() == null ? "" : LoginUtils.getInstance().getUser().getGuid());  // 添加公共版本号，加在 URL 后面
			httpUrlBuilder.addQueryParameter("token", LoginUtils.getInstance().getUser() == null ? "" : LoginUtils.getInstance().getUser().getToken()); // 添加公共版本号，加在 URL 后面
			requestBuilder.url(httpUrlBuilder.build());
			request = requestBuilder.build();
			return chain.proceed(request);
		}
	})



7 SwipeRefreshLayout嵌套ViewPager的滑动冲突问题参考demo的SuperSwipeRefreshLayout类

public class SuperSwipeRefreshLayout extends SwipeRefreshLayout {

    private float startY;
    private float startX;
    // 记录viewPager是否拖拽的标记
    private boolean mIsVpDragger;
    private final int mTouchSlop;

    public SuperSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsVpDragger = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if(mIsVpDragger) {
                    return false;
                }

                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if(distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpDragger = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                mIsVpDragger = false;
                break;
        }
        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev);
    }
}

9.使用AndroidAutoSize进行UI适配，AndroidAutoSize这个库会导致部分依赖库banner的图片被拉伸变形。

10.使用Xbanner进行banner图轮播显示

11. AS运行main()方法报错：SourceSet with name ‘main‘ not found
解决方法：在.idea文件夹下的gradle.xml文件中添加<option name="delegatedBuild" value="false" />，然后重新运行就可以了

12Android广播接收器注册问题：Caused by: java.lang.IllegalArgumentException: Receiver not registered
	一、程序中明明使用以下方法进行了广播的注册和解除注册：java
    
		mContext.registerReceiver(downloadReceiver, filter);
		mContext.unregisterReceiver(downloadReceiver);

		但程序运行过程当中仍是有一下问题：
		android.app.IntentReceiverLeaked: Activity *********** has leaked IntentReceiver *********** that was originally registered here. Are you missing a call to unregisterReceiver()?

		并出现报错：
		Caused by: java.lang.IllegalArgumentException: Receiver not registered: ***************

		后来改为下面方法就能够了：
		mContext.getApplicationContext().registerReceiver(downloadReceiver, filter);
		mContext.getApplicationContext().unregisterReceiver(downloadReceiver);
		
13 Unable to add window -- token android.os.BinderProxy is not valid； is your activity running?

	原因一般是展示dialog的时候用的是异步，或者是另开一个线程。当Activity已经关闭，才调用dialog.show()，此时Activity已经不存在，必然报错。
	if (!xActivity.this.isFinishing())//xActivity即为本界面的Activity   
	{  
	//dialog.show  
	} 

14.ScrollView嵌套GridView导致显示不全的bug
	解决方法：重新设置一下Gridview高度
	
	    /**
     * 计算GridView宽高
     *
     * @param gridView
     */
    public static void calGridViewWidthAndHeight( GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int numColumns = gridView.getNumColumns();
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View itemView = listAdapter.getView(i, null, gridView);
            itemView.measure(  0,0);
            if ((i + 1) % numColumns == 0) {
                totalHeight += itemView.getMeasuredHeight(); // 统计所有子项的总高度
            }

            if ((i + 1) == len && (i + 1) % numColumns != 0) {
                totalHeight += itemView.getMeasuredHeight(); // 统计所有子项的总高度
            }
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + gridView.getVerticalSpacing();
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }
                                                              
15. [androidstudio快速删除代码空行](https://blog.csdn.net/qq_34024778/article/details/109471376)	

	alt + r 打开搜索替换框，，，   使用  ^\s*\n (替换符） ，再勾选上Regex选项框
	
16. 个人中心界面添加刷新功能,
	一.使用SmartRefreshLayout包裹一个唯一的子布局，不然会报错 --》最多只支持3个子View,Most only support three sub view
                                                              
            <com.scwang.smart.refresh.layout.SmartRefreshLayout
                android:id="@+id/smart_refresh_layout"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

                <LinearLayout
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:orientation="vertical">		

                        .....
                        ...
                        ..
                        .

                </LinearLayout>
            </com.scwang.smart.refresh.layout.SmartRefreshLayout>
	
	
	二.代码调用：
	
		smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smart_refresh_layout);
        smartRefreshLayout.setRefreshHeader((RefreshHeader) new ClassicsHeader(getActivity()));
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                userPresenter.getUserInfo(getActivity());
            }
        });

17.app安装包在64位模拟器打开崩溃的bug
	
	问题猜测：应该是没有64库导致的
	
	解决方法：在build.gradle文件中加上以上代码：
		android {
			defaultConfig {
				ndk {
					abiFilters 'armeabi-v7a','arm64-v8a'
				}
		}
}

18 跳转到oppo的游戏中心

		Intent intent = new Intent();
		intent.setData(Uri.parse("oaps://gc/dt?pkg=com.tencent.tmgp.pubgmhd&enterMod=webgame"));
		//这里Intent当然也可传递参数,但是一般情况下都会放到上面的URL中进行传递
		//intent.putExtra("type", "100");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		
19 . Android studio打包默认会打成v2签名的包，如果要改成v1的需要这样修改build.gradle文件中加上以上代码：

	签名配置里面加上：v1SigningEnabled true
					  v2SigningEnabled false

		signingConfigs {
			release {
				keyAlias 'HyGame'
				keyPassword 'hygame147'
				storeFile file('./hygame.keystore')
				storePassword 'hygame147'
				v1SigningEnabled true
				v2SigningEnabled false
			}
		}		
		

20. 所有的dialog和自定义view不要用单例static去创建对象，不然就算有置null代码还是会出现部分情况导致activity无法正常finish的bug

21. gridview的适配器的item布局中不能使用 RelativeLayout	布局作为父控件，不然会导致item子布局高度设置无效的bug

22. 把list等分分为三个list

        <T> List<T> getList(List<T> list, int index) {
            if (list == null || list.size() == 0) return null;
            int number = list.size() / 3;
            switch (index) {
                case 0:
                    return list.subList(0, number);
                case 1:
                    return list.subList(number, number * 2);
                case 2:
                    return list.subList(number * 2, list.size());
            }
            return null;
        }

23. app直接跳转指定小程序

        public void toWxCode() {
            toWxCode("gh_3e4fe915ffb1");
        }

        public void toWxCode(String id) {
            if (api != null) {
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = id; // 填小程序原始id
                req.path = "";                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                api.sendReq(req);
                Log.i("微信认证", "拉起微信");
            } else {
                Log.i("微信认证", "微信认证注册失败或者未注册");
            }
        }		


24. 未设置dialog的style属性时以下设置宽度代码未生效：

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);

	解决方法：在dialog构造函数中添加style属性代码
	
	    private RuleDialog(@NonNull Activity context) {
			this(context, Utils.getStyleId(context,"HYGame_base_fragment_pop"));
			initView(context);
		}
		
	该style属性如下：
	
	    <style name="HYGame_base_fragment_pop" parent="@android:style/Theme.Dialog">
			<item name="android:backgroundDimAmount">0.6</item>
			<item name="android:windowBackground">@android:color/transparent</item>
			<item name="android:windowFrame">@null</item>
			<item name="android:windowNoTitle">true</item>
			<item name="android:windowIsFloating">true</item>
			<item name="android:windowIsTranslucent">true</item>
			<item name="android:background">@android:color/transparent</item>
			<item name="android:windowFullscreen">true</item>
			<item name="android:backgroundDimEnabled">true</item>
		</style>

25. Android ImageView显示长图被拉伸、图片下半部分超长空白等问题
	问题
	直接使用会导致图片拉伸变形
	尝试android:scaleType="fitStart"后图片不会被拉伸，但是ImageView高度和图片高度不一致，在图片下方留下大量空白。

	解决方案
	使用android:scaleType="fitStart"和android:adjustViewBounds="true"。
	


### Contact Me

- Github: github.com/cheng2016
- Email: mitnick.cheng@outlook.com
- QQ: 1102743539	 

# License

    Copyright 2016 cheng2016,Inc.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
