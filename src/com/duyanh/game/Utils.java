package com.duyanh.game;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * bla bla
 */

/**
 *
 * @author Welcome
 */
public class Utils {
    public static List<File> searchForFile(File parent, String name){
        File[] childrens = parent.listFiles();
        List<File> files = Stream.of(childrens).filter((file) -> file.getName().equals(name)).collect(Collectors.toList());
        return files;
    }
}
