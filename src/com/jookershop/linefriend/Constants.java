package com.jookershop.linefriend;

import java.util.HashMap;

import com.jookershop.linefriend4.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Constants {
	
	public static final String KEY_CLICK_AD = "clickad";
	public static final String KEY_SHOW_ALERT = "showalert";
	public static final String KEY_CLICK_ALERT = "clickalert";

	
	public static int TYPE_INTEREST = 0;
	public static int TYPE_PLACE= 1;
	public static int TYPE_CAREER = 2;
	public static int TYPE_OLD = 3;
	public static int TYPE_CONSTELLATION = 4;
	public static int TYPE_MOTION = 5;
	public static int TYPE_ALL = 99;	
//	public static int TYPE_LIVE = 99;
	
//	public static String BASE_URL = "http://54.66.128.152:9001/linefriend/";
//	public static String BASE_URL = "http://10.128.144.128:9000/linefriend/";	
	public static String BASE_URL = "http://www.jookershop.com:9001/linefriend/";
	public static String IMAGE_BASE_URL = "http://www.jookershop.com:9001/linefriend/";
	
//	public static String BASE_URL = "http://54.66.133.177:9001/linefriend/";
//	public static String BASE_URL = "http://192.168.1.110:9000/linefriend/";
//	public static String IMAGE_BASE_URL = "http://54.66.133.177:9001/linefriend/";	
	
	public static Boolean IS_SUPER = false;
	
	public static String TAG = "linefriend";
	
	public static String NICKNAME_STORE_KEY = "NSK";
	public static String LINE_STORE_KEY = "LSK";
	public static String SEX_STORE_KEY = "SEX";
	public static String SEARCH_SEX = "SSEX";
	public static String HIDE_STORE_KEY = "HSK";

	
	public static String INTEREST_STORE_KEY = "ISK";
	public static String [] INTEREST_TITLES = { "欣賞電影", "逛書店", "聽音樂", "逛街購物", "運動健身", "工作賺錢", "打屁聊天", "畫畫", "旅遊", "吃遍美食", "遊戲", "彩妝", "手作品", "唱歌", "其它" };
	public static String [] INTEREST_IDS = {"1", "2", "3", "4", "5", "6","7","8","9","10","11","12","13", "14","15"};	
	public static Integer[] INTEREST_THUMBNAIL_IDS = { R.drawable.movieicon, R.drawable.bookicon,
			R.drawable.music1icon, R.drawable.carticon,
			R.drawable.sporticon, R.drawable.moneyicon,
			R.drawable.chaticon, R.drawable.pencilicon,
			R.drawable.travelicon, R.drawable.food, R.drawable.gameicon,
			R.drawable.femaleicon, R.drawable.tagicon, R.drawable.micicon,
			R.drawable.othericon };	
	
	public static String INTEREST_HEADER_COLOR = "#A9D9D4";
	public static String INTEREST_COLOR = "#009989";
	public static String INTEREST_ENABLE_COLOR = "#016E62";
	public static HashMap<String, String> INTEREST_MAP = new HashMap<String, String>();
	
	public static String LIVE_HEADER_COLOR = "#A9D9D4";
	public static String LIVE_COLOR = "#009989";
	public static String LIVE_ENABLE_COLOR = "#016E62";
	
	
	public static String PLACE_STORE_KEY = "PSK";
	public static String [] PLACE_TITLES = { "基隆市","台北市", "新北市", "桃園縣", "新竹縣市", "苗栗縣", "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義縣市", "臺南市", "高雄市", "屏東縣", "台東縣", "花蓮縣", "宜蘭縣", "外島地區" };
	public static String [] PLACE_IDS = {"1", "2", "3", "4", "5", "6","7","8","9","10","11","12","13", "14","15","16","17","18"};
	public static Integer[] PLACE_THUMBNAIL_IDS = {
        R.drawable.houseicon, R.drawable.house4icon,
        R.drawable.house3icon, R.drawable.house2icon,
        R.drawable.house1icon, R.drawable.house1icon,
        R.drawable.house2icon, R.drawable.house3icon,
        R.drawable.houseicon, R.drawable.house1icon,
        R.drawable.house2icon, R.drawable.house3icon,
        R.drawable.houseicon, R.drawable.house1icon, 
        R.drawable.house2icon,R.drawable.house3icon,
        R.drawable.houseicon,R.drawable.house1icon
};	
	
	public static String PLACE_HEADER_COLOR = "#DED0AB";	
	public static String PLACE_COLOR = "#DEAE2C";//"#EDB92E";
	public static String PLACE_ENABLE_COLOR = "#8C6E1B";
	public static HashMap<String, String> PLACE_MAP = new HashMap<String, String>();
	

	public static String CAREER_STORE_KEY = "CSK";
	public static String [] CAREER_TITLES = { "美容美髮", "行政總務", "餐飲專業", "業務銷售", "營建", "資訊科技", "醫療服務", "教育服務", "金融專業", "軍警消防", "百貨量販", "行銷廣告", "門市管理", "財會稅務","高中職生", "大學專科", "其它專業" };
	public static String [] CAREER_IDS = {"1", "2", "3", "4", "5", "6","7","8","9","10","11","12","13", "14","15","16","17"};
	public static Integer[] CAREER_THUMBNAIL_IDS = {
		R.drawable.hairdressericon, R.drawable.pencilicon,
        R.drawable.food, R.drawable.mailicon,
        R.drawable.house4icon, R.drawable.computericon,
        R.drawable.doctoricon, R.drawable.bookicon,
        R.drawable.moneyicon, R.drawable.policeicon,
        R.drawable.house3icon, R.drawable.marketicon, 
        R.drawable.paymenticon, R.drawable.cashicon,
        R.drawable.peopleicon,R.drawable.peopleicon,
        R.drawable.othericon
	};	
	

	public static String CAREER_HEADER_COLOR = "#D6D9B2";
	public static String CAREER_COLOR = "#858A3D";
	public static String CAREER_ENABLE_COLOR = "#65692F";	
	
	public static HashMap<String, String> CAREER_MAP = new HashMap<String, String>();
	
	
	public static String OLD_STORE_KEY = "OSK";
	public static String [] OLD_TITLES = { "17歲以下", "18-20歲", "21-25歲", "26-30歲", "31-35歲", "36-40歲", "40-45歲", "46歲以上" };
	public static String [] OLD_IDS = {"1", "2", "3", "4", "5", "6","7","8"};
	public static Integer[] OLD_THUMBNAIL_IDS = {
        R.drawable.peopleicon, R.drawable.peopleicon,
        R.drawable.peopleicon, R.drawable.peopleicon,
        R.drawable.peopleicon, R.drawable.peopleicon,
        R.drawable.peopleicon, R.drawable.peopleicon
	};	
	
	public static String OLD_HEADER_COLOR = "#E3BCDE";	
	public static String OLD_COLOR = "#9E3A92";//"#F85931";
	public static String OLD_ENABLE_COLOR = "#612459";//"#AB3E22";
	public static HashMap<String, String> OLD_MAP = new HashMap<String, String>();

	
	public static String CONSTELLATION_STORE_KEY = "CTSK";
	public static String [] CONSTELLATION_TITLES = { "魔羯座", "水瓶座", "雙魚座", "牡羊座", "金牛座", "雙子座", "巨蟹座", "獅子座", "處女座", "天秤座", "天蠍座", "射手座" };
	public static String [] CONSTELLATION_IDS = {"1", "2", "3", "4", "5", "6","7","8", "9","10", "11", "12"};
	public static Integer[] CONSTELLATION_THUMBNAIL_IDS = {
        R.drawable.cl1, R.drawable.cl2,
        R.drawable.cl3, R.drawable.cl4,
        R.drawable.cl5, R.drawable.cl6,
        R.drawable.cl7, R.drawable.cl8,
        R.drawable.cl9, R.drawable.cl10,
        R.drawable.cl11, R.drawable.cl12,
	};	
	
	public static String CONSTELLATION_HEADER_COLOR = "#9DADEB";	
	public static String CONSTELLATION_COLOR = "#5269C7";//"#F85931";
	public static String CONSTELLATION_ENABLE_COLOR = "#23357D";//"#AB3E22";
	public static HashMap<String, String> CONSTELLATION_MAP = new HashMap<String, String>();	
	
	public static String MOTION_STORE_KEY = "MOSK";
	public static String [] MOTION_TITLES = { "善良", "正直", "溫柔", "愛生氣", "大方", "不拘小節", "小固執", "體貼", "忠貞", "宅宅的", "見義勇為", "文靜", "愛助人", "勤儉", "動作慢慢", "體力好", "好奇心重", "中規中矩", "愛笑", "情感豐富", "夜生活"};
	public static String [] MOTION_IDS = {"1", "2", "3", "4", "5", "6","7","8", "9","10", "11", "12","13", "14", "15", "16", "17", "18", "19", "20", "21"};
	public static Integer[] MOTION_IDS_THUMBNAIL_IDS = {R.drawable.cl1, R.drawable.cl1, R.drawable.cl1, 
		R.drawable.cl1,R.drawable.cl1,R.drawable.cl1,
		R.drawable.cl1,R.drawable.cl1,R.drawable.cl1,
		R.drawable.cl1,R.drawable.cl1,R.drawable.cl1,
		R.drawable.cl1,R.drawable.cl1,R.drawable.cl1,
		R.drawable.cl1,R.drawable.cl1,R.drawable.cl1,
		R.drawable.cl1,R.drawable.cl1,R.drawable.cl1};	
	

	public static String MOTION_HEADER_COLOR = "#D66B8B";	
	public static String MOTION_COLOR = "#AD4967";//"#F85931";
	public static String MOTION_ENABLE_COLOR = "#63192F";//"#AB3E22";
	public static HashMap<String, String> MOTION_MAP = new HashMap<String, String>();	
	
	
	public static String NOTIFY_HOUR = "nh";
	public static String NOTIFY_STATUS = "ns";
	public static String HIDE_SETTING = "hs";
	public static String NOTIFY_MUSIC = "nm";
	public static String NOTIFY_VIR = "nv";
	
	public static String [] DISCUSS_MENU_TITLES = { "閒聊", "美食", "遊戲", "愛情甘苦", "自我推薦"};
	public static String [] DISCUSS_MENU_DESCS = { "閒閒沒事來po文聊天...", "我發現這裡有好吃的，讓我帶你去", "就是愛整天打電動", "在愛情裡面您一定不是永遠的輸家", "想要秀自己給大家看到嗎？"};
	public static String [] DISCUSS_MENU_NUM = { "1+", "150+", "42+", "12+", "200+"};
	
	
	public static void init() {
		for(int index = 0; index < INTEREST_IDS.length; index ++) {
			INTEREST_MAP.put(INTEREST_IDS[index], INTEREST_TITLES[index]);
		}
		for(int index = 0; index < PLACE_IDS.length; index ++) {
			PLACE_MAP.put(PLACE_IDS[index], PLACE_TITLES[index]);
		}
		for(int index = 0; index < CAREER_IDS.length; index ++) {
			CAREER_MAP.put(CAREER_IDS[index], CAREER_TITLES[index]);
		}
		for(int index = 0; index < OLD_IDS.length; index ++) {
			OLD_MAP.put(OLD_IDS[index], OLD_TITLES[index]);
		}		

		for(int index = 0; index < CONSTELLATION_IDS.length; index ++) {
			CONSTELLATION_MAP.put(CONSTELLATION_IDS[index], CONSTELLATION_TITLES[index]);
		}	
		
		for(int index = 0; index < MOTION_IDS.length; index ++) {
			MOTION_MAP.put(MOTION_IDS[index], MOTION_TITLES[index]);
		}			
	}
	
	
	
	
	
}
