/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/12/9 13:04</create-date>
 *
 * <copyright file="DemoCustomDictionary.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.hankcs.demo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.dictionary.BaseSearcher;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;

import java.util.Map;

/**
 * 演示用户词典的动态增删
 *
 * @author hankcs
 */
public class DemoCustomDictionary
{
    public static void main(String[] args)
    {
        // 动态增加
        CustomDictionary.add("孔雀女");
        // 强行插入
        CustomDictionary.insert("码农", "nz 1024");
        // 删除词语（注释掉试试）
//        CustomDictionary.remove("码农");
        System.out.println(CustomDictionary.add("裸婚", "v 2 nz 1"));
        System.out.println(CustomDictionary.get("裸婚"));

        String text = "码农和孔雀女裸婚了";  // 怎么可能噗哈哈！

        // AhoCorasickDoubleArrayTrie自动机分词
        final char[] charArray = text.toCharArray();
        CoreDictionary.trie.parseText(charArray, new AhoCorasickDoubleArrayTrie.IHit<CoreDictionary.Attribute>()
        {
            @Override
            public void hit(int begin, int end, CoreDictionary.Attribute value)
            {
                System.out.printf("[%d:%d]=%s %s\n", begin, end, new String(charArray, begin, end - begin), value);
            }
        });
        // trie树分词
        BaseSearcher searcher = CustomDictionary.getSearcher(text);
        Map.Entry entry;
        while ((entry = searcher.next()) != null)
        {
            System.out.println(entry);
        }

        // 标准分词
        System.out.println(HanLP.segment(text));
    }
}