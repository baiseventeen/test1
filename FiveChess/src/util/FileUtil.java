package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import elements.Composition;
import elements.Player;

public class FileUtil {
	public static void writeData(String data,String fileName, boolean mode) throws IOException {
		String fileNametwo = "data/"+fileName;
		FileWriter fw = new FileWriter(fileNametwo,mode);//false覆盖模式,true追加模式
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(data);
		bw.newLine();//加入换行
		//刷新流
		bw.flush();
		bw.close();
		fw.close();//关闭流时候先关闭大的再关闭小的
	}
	
	public static List<Player>  readPlayer(String fileName) throws IOException {
		String fileNameTwo = "data/"+fileName;
		List<Player> list =new ArrayList<Player>();
		BufferedReader br=new BufferedReader(new FileReader(fileNameTwo));
		String str=null;
		while ((str=br.readLine())!=null) {
			Player player = new Player();
			String[] arr_str = str.split(":");
			player.setUsername(arr_str[1]);
			player.setPassword(arr_str[3]);
			list.add(player);
		}
		br.close();
		return list;
	}
	
	public static List<Composition>  readComposition(String fileName) throws IOException {
		String filePath = "data/"+fileName;
		List<Composition> list =new ArrayList<Composition>();
		BufferedReader br=new BufferedReader(new FileReader(filePath));
		String str=null;
		while ((str=br.readLine())!=null) {
			Composition composition = new Composition();
			String[] arr_str = str.split(":");
			composition.setUsername(arr_str[1]);
			composition.setName1(arr_str[3]);
			composition.setName2(arr_str[5]);
			composition.setGamename(arr_str[7]);
			
			String[] arr_str2 = arr_str[9].split(",") ;
			long[] times = new long[256];
			for(int i = 0; i < arr_str2.length; i++) {
				times[i] = Long.valueOf(arr_str2[i]);
			}
			composition.setTimes(times);
			
			String[] arr_str3 = arr_str[11].split(",") ;
			int[][] allChess = new int[16][16];
			for(int i = 0; i < 16; i++) {
				for(int j = 0; j < 16; j++) {
					allChess[i][j] = Integer.parseInt(arr_str3[i * 16 + j]);
				}
			}
			composition.setAllChess(allChess);
			list.add(composition);
		}
		br.close();
		return list;
	}
}
