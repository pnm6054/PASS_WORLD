package passworld;
import javax.swing.JFrame;

public class test_4all {
	
	First loginView;
    GUI_Main testFrm;
   
    public static void main(String[] args) {
       
        // ����Ŭ���� ����
    	test_4all main = new test_4all();
        main.loginView = new First(); // �α���â ���̱�
        main.loginView.setMain(main); // �α���â���� ���� Ŭ����������
    }
   
    // �׽�Ʈ������â
    public void showFrameTest(){
        loginView.dispose(); // �α���â�ݱ�
        this.testFrm = new GUI_Main(); // �׽�Ʈ������ ����
    }
}