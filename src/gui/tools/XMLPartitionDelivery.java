package gui.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.jdom2.input.SAXBuilder;
import org.sinfile.parsers.SinFile;

import flashsystem.CommandFlasher.UfsInfos;
import gui.FileSelector;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Element;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class XMLPartitionDelivery {

	private Vector<String> partitions = new Vector<String>();
	static final Logger logger = LogManager.getLogger(XMLPartitionDelivery.class);
	String folder = "";
	UfsInfos ufs_infos = null;

	public XMLPartitionDelivery(File xmlsource) throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		FileInputStream fin = new FileInputStream(xmlsource);
		Document document = builder.build(fin);
		document.getRootElement().getChild("PARTITION_IMAGES");
		//String spaceid = document.getRootElement().getAttribute("SPACE_ID").getValue();
		Iterator<Element> i=document.getRootElement().getChild("PARTITION_IMAGES").getChildren().iterator();
		while (i.hasNext()) {
			Element e = i.next();
			partitions.addElement(e.getAttributeValue("PATH"));
		}
		fin.close();
	}

	public void setUfsInfos(UfsInfos infos) {
		ufs_infos = infos;
		if (ufs_infos != null) {
			Map<Integer,Integer> partcount = new HashMap<Integer,Integer>();
			Enumeration<String> files = partitions.elements();
			while (files.hasMoreElements()) {
				String file=files.nextElement();
				if (file.contains("LUN0")) {
					if (partcount.get(0)==null) partcount.put(0, 1);
					else partcount.put(0, partcount.get(0)+1);
				}
				if (file.contains("LUN1")) {
					if (partcount.get(1)==null) partcount.put(1, 1);
					else partcount.put(1, partcount.get(1)+1);
				}
				if (file.contains("LUN2")) {
					if (partcount.get(2)==null) partcount.put(2, 1);
					else partcount.put(2, partcount.get(2)+1);
				}
				if (file.contains("LUN3")) {
					if (partcount.get(3)==null) partcount.put(3, 1);
					else partcount.put(3, partcount.get(3)+1);
				}
				if (file.contains("LUN4")) {
					if (partcount.get(4)==null) partcount.put(4, 1);
					else partcount.put(4, partcount.get(4)+1);
				}
				if (file.contains("LUN5")) {
					if (partcount.get(5)==null) partcount.put(5, 1);
					else partcount.put(5, partcount.get(5)+1);
				}
				if (file.contains("LUN6")) {
					if (partcount.get(6)==null) partcount.put(6, 1);
					else partcount.put(6, partcount.get(6)+1);
				}
			}
			files = partitions.elements();
			while (files.hasMoreElements()) {
				String file=files.nextElement();
				if ( file.contains("LUN0") && partcount.get(0)>1) {
					if (!file.contains(String.valueOf(ufs_infos.getLunSize(0)))) {
						partitions.remove(file);
					}	
				}
				if ( file.contains("LUN1")  && partcount.get(1)>1) {
					if (!file.contains(String.valueOf(ufs_infos.getLunSize(1)))) {
						partitions.remove(file);
					}	
				}
				if ( file.contains("LUN2")   && partcount.get(2)>1) {
					if (!file.contains(String.valueOf(ufs_infos.getLunSize(2)))) {
						partitions.remove(file);
					}	
				}
				if ( file.contains("LUN3")   && partcount.get(3)>1) {
					if (!file.contains(String.valueOf(ufs_infos.getLunSize(3)))) {
						partitions.remove(file);
					}	
				}
				if ( file.contains("LUN4")   && partcount.get(4)>1) {
					if (!file.contains(String.valueOf(ufs_infos.getLunSize(4)))) {
						partitions.remove(file);
					}	
				}
				if ( file.contains("LUN5")   && partcount.get(5)>1) {
					if (!file.contains(String.valueOf(ufs_infos.getLunSize(5)))) {
						partitions.remove(file);
					}	
				}
				if ( file.contains("LUN6")   && partcount.get(6)>1) {
					if (!file.contains(String.valueOf(ufs_infos.getLunSize(6)))) {
						partitions.remove(file);
					}	
				}
			}
		}
	}

	public String getMatchingFile(String match, Shell shell) {
		Vector<String> matched = new Vector<String>();
		Iterator<String> file = partitions.iterator();
		while(file.hasNext()) {
			String name = file.next();
			if (SinFile.getShortName(name).equals(match))
				matched.add(name);
		}
		if (matched.size()==1)
			return (folder.length()>0?folder+"/":"")+matched.get(0);
		if (ufs_infos==null) {
			if (matched.size()>0) {
				String result=WidgetTask.getPartition(matched, shell);
				if (result.length() > 0) 
					return (folder.length()>0?folder+"/":"")+result;
				else
					return null;
			}
		}
		return null;
	}

	public void setFolder(String folder) {
		this.folder=folder+File.separator+"partition";
	}

	public Enumeration<String> getFiles() {
        return partitions.elements();
	}

}