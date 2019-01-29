package com.example.demo.controller;


import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;


import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Lucence {

    public static void main(String[] args) throws Exception {
        ResultSet recrd = getConn();

        // 存放在内存中
        Directory dir = new RAMDirectory();
        // Alternative 1: Search in dir with txt files
        new Lucence().createIndex(dir);
        // Alternative 2: Search in dir with txt files
        new Lucence().createIndexWithMysql(dir, recrd);
        new Lucence().search(dir);
    }


    public static ResultSet getConn() {                                       //建立数据库连接，并返回结果
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test";

            Connection conn = DriverManager.getConnection(url, "root", "password");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select * from Student";
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建索引
     */
    public void createIndex(Directory dir) throws IOException {
        IndexWriter indexWriter = null;
        // 1. 创建 Directory (索引存放位置)，这里是参数dir

        // 2. 创建IndexWriter 写索引
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(dir, iwc);

        // 3. 创建Document 对象 field
        Document document;
        File file = new File("file");
        for (File f : file.listFiles()) {
            document = new Document();

            // 4. 为Documen添加field
            document.add(new Field("content", FileUtils.readFileToString(f, "utf-8"), TextField.TYPE_STORED));
            document.add(new TextField("fileName", f.getName(), Field.Store.YES));
            document.add(new StringField("filePath", f.getAbsolutePath(), Field.Store.YES));
            // 5. 通过IndexWriter 添加文档到索引中
            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }

    /**
     * 创建索引
     */
    public void createIndexWithMysql(Directory dir, ResultSet recrd) throws IOException, SQLException {
        IndexWriter indexWriter = null;
        // 1. 创建 Directory (索引存放位置)，这里是参数dir

        // 2. 创建IndexWriter 写索引
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        try (IndexWriter indexWriter1 = indexWriter = new IndexWriter(dir, iwc)) {
        }

        // 3. 创建Document 对象 field
        Document document;
        // 3b. 从mysql得到数据
        while (recrd.next()) {
            document = new Document();

//            document.add(new Field("content", recrd.getString("user_id"), Field.Store.YES));
//            document.add(new Field("username", recrd.getString("username"), Field.Store.YES));
//            document.add(new Field("address", recrd.getString("address"), Field.Store.YES));

            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }

    /**
     * 根据内容搜索
     *
     * @param dir
     * @throws Exception
     */
    public void search(Directory dir) throws Exception {
        IndexReader indexReader = null;
        // 1. 创建 Directory，这里通过参数传递过来的dir
        // Directory dir = FSDirectory.open(new File("file/index").toPath()); // 本地磁盘

        // 2. 创建 IndexReader
        indexReader = DirectoryReader.open(dir);

        // 3. 创建 IndexSearch
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 4. 创建搜索的Query
        // 创建parse确定搜索的内容，第二个参数为搜索的file
        QueryParser queryParser = new QueryParser("content", new StandardAnalyzer());

        // 创建Query，表示搜索域中的内容
        Query query = queryParser.parse("enduml");

        // 5. 搜索并返回 TopDocs
        TopDocs topDocs = indexSearcher.search(query, 10);

        // 6. 根据topDocs 获得 scoreDocs
        ScoreDoc[] sourceDocs = topDocs.scoreDocs;

        for (ScoreDoc doc : sourceDocs) {
            // 获取Document对象
            Document document = indexSearcher.doc(doc.doc);

            // 根据Document对象获取需要的值
            System.out.println(document.get("fileName"));
            System.out.println(document.get("content"));
        }
        indexReader.close();
    }
}