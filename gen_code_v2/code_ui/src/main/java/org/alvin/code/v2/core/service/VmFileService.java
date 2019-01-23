package org.alvin.code.v2.core.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
/**
 * 文件操作
 */
public class VmFileService {

	/**
	 * 扫描文件并执行
	 */
	public List<String> scanTemplate(String dirName, String suffix) throws IOException {
		File dir = new File(dirName).getCanonicalFile();
		List<String> classList = Lists.newArrayList();

		LinkedList<File> files = Lists.newLinkedList();
		if (!dir.exists() || !dir.isDirectory()) {
			log.error("没有找到对应的目录");
			return Lists.newArrayList();
		}
		files.add(dir);
		// 循环读取目录以及子目录下的所有类文件
		while (!files.isEmpty()) {
			File f = files.removeFirst();
			if (f.isDirectory()) {
				File[] fs = f.listFiles();
				int i = 0;
				for (File childFile : fs) {
					files.add(i++, childFile);
				}
				continue;
			}
			if (!f.getName().toLowerCase().endsWith(suffix)) {
				continue;
			}
			String subPath = f.getAbsolutePath().substring(dir.getAbsolutePath().length());
			classList.add(subPath);
		}

		return classList;
	}
}
