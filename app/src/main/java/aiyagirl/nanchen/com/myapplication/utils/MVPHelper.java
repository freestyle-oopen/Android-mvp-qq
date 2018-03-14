package aiyagirl.nanchen.com.myapplication.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/11/7.
 */

public class MVPHelper {

    static String[] activitys = {"Login"};                             //Activity前缀类名
    static String[] fragments = {"Me"};                              //Fragment前缀类名
    static File basePath = new File("D:\\ex\\202\\11");     //生成绝对路径（必填）
    static String packageName = "com.ren.test";              //项目包名（必填）
    static String[] all;



    public void addition_isCorrect() {

        //1、创建项目目录
        if (!basePath.exists()) {
            basePath.mkdirs();
        }
        try {
            all = new String[activitys.length + fragments.length];
            System.arraycopy(activitys, 0, all, 0, activitys.length);
            System.arraycopy(fragments, 0, all, activitys.length, fragments.length);
            creatAllPath(basePath);      //创建所有目录
            newBaseObject();            //创建所有base类
            newModleFile();             //创建Modle文件
            newPresenter();              //创建Presenter文件
            newContract();               //创建接口类Contract
            newActivity();               //创建Activity类
            newFragment();               //创建Fragment类
            newLayout();                  //创建所有Fragment和Activity的布局
            System.out.println("======完成======");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发生异常：" + e.toString());
        }
    }

    private void newLayout() {
        File xFilePath = new File(basePath, "\\layout\\");
        try {
            for (int i = 0; i < activitys.length; i++) {
                PrintWriter writer = new PrintWriter(new FileOutputStream(new File(xFilePath, "activity_" + activitys[i].toLowerCase() + ".xml")));
                writer.print(getLayoutMsg());
                writer.close();
            }
            for (int i = 0; i < fragments.length; i++) {
                PrintWriter fwriter = new PrintWriter(new FileOutputStream(new File(xFilePath, "fragment_" + fragments[i].toLowerCase() + ".xml")));
                fwriter.print(getLayoutMsg());
                fwriter.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("新建xml发生异常：" + e.toString());
        }
    }

    private void newFragment() {
        File fFilePath = new File(basePath, "\\ui\\fragment\\");
        try {
            for (int i = 0; i < fragments.length; i++) {
                File fFile = new File(fFilePath, fragments[i] + "Fragment.java");
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(fFile));
                printWriter.print("package " + packageName + ".ui.fragment;\n\nimport android.os.Bundle;\n" +
                        "import " + packageName + ".modle." + fragments[i] + "Modle;\nimport " + packageName + ".R;\n" +
                        "import " + packageName + ".presenter." + fragments[i] + "Presenter;\n" +
                        "import " + packageName + ".ui.contract." + fragments[i] + "Contract;\n\n" +
                        "public class " + fragments[i] + "Fragment extends BaseFragment<" + fragments[i] + "Presenter," + fragments[i] + "Modle> implements " + fragments[i] + "Contract.View {\n" +
                        "    @Override\n    public void showMessage(String msg) {\n\n    }\n\n    @Override\n" +
                        "    public int layoutResId() {\n        return R.layout.fragment_" + fragments[i].toLowerCase() + ";\n    }\n\n" +
                        "    @Override\n    public void onBind(Bundle savedInstanceState) {\n\n    }\n}");
                printWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("新建Fragment发生异常：" + e.toString());
        }
    }

    private void newActivity() {
        File aFilePath = new File(basePath, "\\ui\\activity\\");
        try {
            for (int i = 0; i < activitys.length; i++) {
                File aFile = new File(aFilePath, activitys[i] + "Activity.java");
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(aFile));
                printWriter.print("package " + packageName + ".ui.activity;\n\nimport android.os.Bundle;\n" +
                        "import " + packageName + ".modle." + activitys[i] + "Modle;\nimport " + packageName + ".R;\n" +
                        "import " + packageName + ".presenter." + activitys[i] + "Presenter;\n" +
                        "import " + packageName + ".ui.contract." + activitys[i] + "Contract;\n\n" +
                        "public class " + activitys[i] + "Activity extends BaseActivity<" + activitys[i] + "Presenter," + activitys[i] + "Modle> implements " + activitys[i] + "Contract.View {\n" +
                        "    @Override\n    public void showMessage(String msg) {\n\n    }\n\n    @Override\n" +
                        "    public int layoutResId() {\n        return R.layout.activity_" + activitys[i].toLowerCase() + ";\n    }\n\n" +
                        "    @Override\n    public void onBind(Bundle savedInstanceState) {\n\n    }\n}");
                printWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("新建Activity发生异常：" + e.toString());
        }
    }

    private void newBaseObject() {
        try {
            File mFile = new File(basePath, "\\modle\\BaseModle.java");
            if (!mFile.exists()) {
                mFile.createNewFile();
            }
            PrintWriter writer = new PrintWriter(new FileOutputStream(mFile));
            writer.print(getMBaseStr());
            writer.close();
            File vFile = new File(basePath, "\\ui\\BaseView.java");
            if (!vFile.exists()) {
                vFile.createNewFile();
            }
            PrintWriter vWriter = new PrintWriter(new FileOutputStream(vFile));
            vWriter.print(getVBaseStr());
            vWriter.close();
            File pFile = new File(basePath, "\\presenter\\BasePresenter.java");
            if (!pFile.exists()) {
                pFile.createNewFile();
            }
            PrintWriter pWriter = new PrintWriter(new FileOutputStream(pFile));
            pWriter.print(getPBaseStr());
            pWriter.close();
            File aFile = new File(basePath, "\\ui\\activity\\BaseActivity.java");
            if (!aFile.exists()) {
                aFile.createNewFile();
            }
            PrintWriter aWriter = new PrintWriter(new FileOutputStream(aFile));
            aWriter.print(getABaseStr());
            aWriter.close();
            File fFile = new File(basePath, "\\ui\\fragment\\BaseFragment.java");
            if (!fFile.exists()) {
                fFile.createNewFile();
            }
            PrintWriter fWriter = new PrintWriter(new FileOutputStream(fFile));
            fWriter.print(getFBaseStr());
            fWriter.close();
            File uFile = new File(basePath, "\\utils\\InstanceUtils.java");
            if (!uFile.exists()) {
                uFile.createNewFile();
            }
            PrintWriter uWriter = new PrintWriter(new FileOutputStream(uFile));
            uWriter.print(getUBaseStr());
            uWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("新建Base类出错：" + e.toString());
        }

    }

    /**
     * 创建所有目录
     */
    private void creatAllPath(File basePath) {
        File mFilePath = new File(basePath, "\\modle\\");
        if (!mFilePath.exists()) {
            mFilePath.mkdir();
        }
        File vFilePath = new File(basePath, "\\ui\\");
        if (!vFilePath.exists()) {
            vFilePath.mkdir();
        }
        File pFilePath = new File(basePath, "\\presenter\\");
        if (!pFilePath.exists()) {
            pFilePath.mkdir();
        }
        File uFilePath = new File(basePath, "\\utils\\");
        if (!uFilePath.exists()) {
            uFilePath.mkdir();
        }
        File cFilePath = new File(vFilePath, "\\contract\\");
        if (!cFilePath.exists()) {
            cFilePath.mkdir();
        }
        File aFilePath = new File(vFilePath, "\\activity\\");
        if (!aFilePath.exists()) {
            aFilePath.mkdir();
        }
        File fFilePath = new File(vFilePath, "\\fragment\\");
        if (!fFilePath.exists()) {
            fFilePath.mkdir();
        }
        File xFilePath = new File(basePath, "\\layout\\");
        if (!xFilePath.exists() && all.length>0) {
            xFilePath.mkdir();
        }
    }

    /**
     * 获取布局内容
     */
    private String getLayoutMsg() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        stringBuffer.append("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    android:orientation=\"vertical\">\n\n</LinearLayout>");
        return stringBuffer.toString();
    }

    private String getMBaseStr() {
        StringBuffer mBaseStr = new StringBuffer();
        mBaseStr.append("package " + packageName + ".modle;\n\npublic interface BaseModle {\n\n}");
        return mBaseStr.toString();
    }

    private void newContract() {
        File mFilePath = new File(basePath, "\\ui\\contract\\");
        try {
            for (int i = 0; i < all.length; i++) {
                File mFile = new File(mFilePath, all[i] + "Contract.java");
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(mFile));
                printWriter.print("package " + packageName + ".ui.contract;\n\n" +
                        "import " + packageName + ".modle.BaseModle;\nimport " + packageName + ".presenter.BasePresenter;\n" +
                        "import " + packageName + ".ui.BaseView;\n\npublic class " + all[i] + "Contract {\n    public interface Modle extends BaseModle {\n\n" +
                        "    }\n\n    public interface View extends BaseView {\n    }\n\n" +
                        "    public static abstract class Presenter extends BasePresenter<View, Modle> {\n    }\n}");
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("新建Contract包出错：" + e.toString());
        }
    }

    private void newPresenter() {
        File mFilePath = new File(basePath, "\\presenter\\");
        try {
            for (int i = 0; i < all.length; i++) {
                File mFile = new File(mFilePath, all[i] + "Presenter.java");
                PrintWriter writer = new PrintWriter(new FileOutputStream(mFile));
                writer.print("package " + packageName + ".presenter;\n\nimport " + packageName + ".ui.contract." + all[i] + "Contract;\n\n" +
                        "public class " + all[i] + "Presenter extends " + all[i] + "Contract.Presenter {\n\n}");
                writer.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("新建Presenter包出错：" + e.toString());
        }
    }


    private void newModleFile() {
        File mFilePath = new File(basePath, "\\modle\\");
        try {
            for (int i = 0; i < all.length; i++) {
                File mFile = new File(mFilePath, all[i] + "Modle.java");
                PrintWriter writer = new PrintWriter(new FileOutputStream(mFile));
                writer.print("package " + packageName + ".modle;\n\nimport " + packageName + ".ui.contract." + all[i] + "Contract;\n\n" +
                        "public class " + all[i] + "Modle implements " + all[i] + "Contract.Modle {\n\n}");
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("新建Modle包出错：" + e.toString());
        }
    }


    public String getVBaseStr() {
        StringBuffer vBaseStr = new StringBuffer();
        vBaseStr.append("package " + packageName + ".ui;\n\npublic interface BaseView {\n\n    void showMessage(String msg);\n}");
        return vBaseStr.toString();
    }

    public String getPBaseStr() {
        StringBuffer pBaseStr = new StringBuffer();
        pBaseStr.append("package " + packageName + ".presenter;\n\n" +
                "import android.app.Activity;\n" +
                "import android.content.Intent;\n" +
                "import android.support.annotation.NonNull;\n" +
                "import android.support.v4.app.Fragment;\n" +
                "import " + packageName + ".modle.BaseModle;\n" +
                "import " + packageName + ".ui.BaseView;\n" +
                "import " + packageName + ".utils.InstanceUtils;\n\n");
        pBaseStr.append("public class BasePresenter<V extends BaseView, M extends BaseModle> {\n" +
                "    private V mView;\n    private M mModle;\n\n    public void onCreate(@NonNull Activity activity) {\n        this.mView = (V) activity;\n" +
                "        this.mModle = InstanceUtils.getInstance(activity, 1);\n        if (!(activity instanceof BaseView)) {\n" +
                "            throw new IllegalArgumentException(activity.getClass().getSimpleName() + \" is a not supported type which must implements BaseView(创建P失败)\");\n" +
                "        }\n        if (mModle == null) {\n            throw new IllegalArgumentException(\"can not get the ParameterizedType of(创建P失败)\" + activity.getClass().getSimpleName());\n" +
                "        }\n}\n\n    public void onCreate(@NonNull Fragment fragment) {\n        this.mView = (V) fragment;\n" +
                "        this.mModle = InstanceUtils.getInstance(fragment, 1);\n\n        if (!(fragment instanceof BaseView)) {\n" +
                "            throw new IllegalArgumentException(fragment.getClass().getSimpleName() + \" is a not supported type which must implements BaseView(创建P失败)\");\n" +
                "        }\n        if (mModle == null) {\n" +
                "            throw new IllegalArgumentException(\"can not get the ParameterizedType of(创建P失败)\" + fragment.getClass().getSimpleName());\n" +
                "        }\n    }\n\n    public V getView() {\n        return mView;\n    }\n\n    public M getModle() {\n        return mModle;\n" +
                "    }\n\n");
        pBaseStr.append("    public void onActivityResult(int requestCode, int resultCode, Intent data) {\n    }\n\n" +
                "    public void onResume() {\n\n    }\n\n    public void onPause() {\n    }\n\n" +
                "    public void onStop() {\n\n    }\n\n    public void onDestroy() {\n    }\n}");
        return pBaseStr.toString();
    }

    public String getABaseStr() {
        StringBuffer aBaseStr = new StringBuffer();
        aBaseStr.append("package " + packageName + ".ui.activity;\n\nimport android.content.Intent;\nimport android.os.Bundle;\n" +
                "import android.support.v7.app.AppCompatActivity;\nimport " + packageName + ".modle.BaseModle;\n" +
                "import " + packageName + ".presenter.BasePresenter;\nimport " + packageName + ".utils.InstanceUtils;\n\n" +
                "public abstract class BaseActivity<P extends BasePresenter , M extends BaseModle> extends AppCompatActivity {\n\n" +
                "    private P mPresenter;\n" +
                "    public abstract int layoutResId();\n" +
                "    public abstract void onBind(Bundle savedInstanceState);\n\n" +
                "    @Override\n    protected void onCreate(Bundle savedInstanceState) {\n        super.onCreate(savedInstanceState);\n" +
                "        if(layoutResId()==0){\n            throw new RuntimeException(\"Activity--No layout is set\");\n" +
                "        }\n        setContentView(layoutResId());\n        mPresenter = InstanceUtils.getInstance(this, 0);\n" +
                "        if (mPresenter != null) {\n            mPresenter.onCreate(this);\n        }\n" +
                "        this.onBind(savedInstanceState);\n    }\n\n" +
                "    public P getmPresenter() {\n        return mPresenter;\n    }\n\n    @Override\n" +
                "    protected void onActivityResult(int requestCode, int resultCode, Intent data) {\n" +
                "        if(mPresenter != null){\n            mPresenter.onActivityResult(requestCode,resultCode,data);\n        }\n" +
                "        super.onActivityResult(requestCode, resultCode, data);\n    }\n\n    @Override\n" +
                "    protected void onResume() {\n        if (mPresenter != null) mPresenter.onResume();\n        super.onResume();\n" +
                "    }\n\n    @Override\n    protected void onPause() {\n        if (mPresenter != null) mPresenter.onPause();\n" +
                "        super.onPause();\n    }\n\n    @Override\n    protected void onStop() {\n        if (mPresenter != null) mPresenter.onStop();\n" +
                "        super.onStop();\n    }\n\n    @Override\n    protected void onDestroy() {\n" +
                "        if (mPresenter != null) mPresenter.onDestroy();\n        super.onDestroy();\n    }\n}\n");
        return aBaseStr.toString();
    }

    public String getFBaseStr() {
        StringBuffer fBaseStr = new StringBuffer();
        fBaseStr.append("package " + packageName + ".ui.fragment;\n\n" +
                "import android.content.Intent;\nimport android.os.Bundle;\nimport android.support.annotation.Nullable;\n" +
                "import android.support.v4.app.Fragment;\nimport android.view.LayoutInflater;\n" +
                "import android.view.View;\nimport android.view.ViewGroup;\n" +
                "import " + packageName + ".modle.BaseModle;\nimport " + packageName + ".presenter.BasePresenter;\n" +
                "import " + packageName + ".utils.InstanceUtils;\n\npublic abstract class BaseFragment<P extends BasePresenter , M extends BaseModle> extends Fragment{\n\n" +
                "    private P mPresenter;\n    public abstract int layoutResId();\n    public abstract void onBind(Bundle savedInstanceState);\n\n" +
                "    @Nullable\n    @Override\n" +
                "    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {\n" +
                "        if(layoutResId()==0){\n            throw new RuntimeException(\"Fragment--No layout is set\");\n" +
                "        }\n      View  mContentView = inflater.inflate(layoutResId(), container, false);\n" +
                "        return mContentView;\n    }\n\n    @Override\n    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {\n" +
                "        super.onViewCreated(view, savedInstanceState);\n        mPresenter = InstanceUtils.getInstance(this, 0);\n" +
                "        if (mPresenter != null) {\n            mPresenter.onCreate(this);\n        }\n        this.onBind(savedInstanceState);\n" +
                "    }\n\n    public P getmPresenter() {\n        return mPresenter;\n    }\n    @Override\n" +
                "    public void onActivityResult(int requestCode, int resultCode, Intent data) {\n        if(mPresenter != null){\n" +
                "            mPresenter.onActivityResult(requestCode,resultCode,data);\n        }\n        super.onActivityResult(requestCode, resultCode, data);\n" +
                "    }\n    @Override\n    public void onResume() {\n        if (mPresenter != null) mPresenter.onResume();\n" +
                "        super.onResume();\n    }\n\n    @Override\n    public void onPause() {\n        if (mPresenter != null) mPresenter.onPause();\n" +
                "        super.onPause();\n    }\n\n    @Override\n    public void onStop() {\n        if (mPresenter != null) mPresenter.onStop();\n" +
                "        super.onStop();\n    }\n\n    @Override\n    public void onDestroy() {\n" +
                "        if (mPresenter != null) mPresenter.onDestroy();\n        super.onDestroy();\n    }\n}\n");
        return fBaseStr.toString();
    }

    public String getUBaseStr() {
        StringBuffer uBaseStr = new StringBuffer();
        uBaseStr.append("package " + packageName + ".utils;\n\nimport java.lang.reflect.ParameterizedType;\n\n" +
                "public class InstanceUtils {\n\n    private InstanceUtils() {\n    }\n\n    public static <T> T getInstance(Object o, int i) {\n" +
                "        if (o.getClass().getGenericSuperclass() instanceof ParameterizedType) {\n            Class mClass = (Class<T>) ((ParameterizedType) (o.getClass()\n" +
                "                    .getGenericSuperclass())).getActualTypeArguments()[i];\n            return getInstance(mClass);\n" +
                "        }\n        return null;\n    }\n\n    public static <T> T getInstance(Class clazz) {\n        try {\n" +
                "            return (T) clazz.newInstance();\n        } catch (InstantiationException e) {\n            e.printStackTrace();\n" +
                "        } catch (IllegalAccessException e) {\n            e.printStackTrace();\n        }\n        return null;\n" +
                "    }\n\n    public static Class<?> forName(String className) {\n        try {\n            return Class.forName(className);\n" +
                "        } catch (ClassNotFoundException e) {\n            e.printStackTrace();\n        }\n        return null;\n    }\n}\n");
        return uBaseStr.toString();
    }

}
