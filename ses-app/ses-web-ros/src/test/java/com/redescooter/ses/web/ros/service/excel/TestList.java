package com.redescooter.ses.web.ros.service.excel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TestList {

    @Test
    public void listDeduplication() {

        List<User1> list = new ArrayList<User1>();
        list.add(new User1("1", "张三1"));
        list.add(new User1("1", "张三2"));
        list.add(new User1("2", "张三3"));
        list.add(new User1("2", "张三4"));
        list.add(new User1("3", "张三5"));
        System.out.println("去重前：");
        for (User1 user : list) {
            System.out.println(user);
        }
        System.out.println("去重后：");
        List<User1> newList = removeDuplicateUser(list);
        for (User1 user : newList) {
            System.out.println(user);
        }
    }

    private static ArrayList<User1> removeDuplicateUser(List<User1> users) {
        Set<User1> set = new TreeSet<User1>(new Comparator<User1>() {
            @Override
            public int compare(User1 o1, User1 o2) {
                // 字符串,则按照asicc码升序排列
                return o1.getId().compareTo(o2.getId());
            }
        });
        set.addAll(users);
        return new ArrayList<User1>(set);
    }

}

class User1 {

    private String id;
    private String name;

    public User1(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User1 [id=" + id + ", name=" + name + "]";
    }


    class User2 {

        private String id;
        private String name;

        public User2(String id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            User2 u = (User2) obj;
            return id.equals(u.id);
        }

        @Override
        public int hashCode() {
            String in = id;
            return in.hashCode();
        }

        @Override
        public String toString() {
            return "User2 [id=" + id + ", name=" + name + "]";
        }
    }
}