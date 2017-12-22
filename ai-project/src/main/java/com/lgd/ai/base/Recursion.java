package com.lgd.ai.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/8/22 10:20
 */
public class Recursion {

//    [
//        {"id":"1","pid":"-1","name":"商品目录"},
//        {"id":"11","pid":"1","name":"日用品"},
//        {"id":"12","pid":"1","name":"食品"},
//        {"id":"111","pid":"11","name":"洗发水"},
//        {"id":"1111","pid":"111","name":"霸王"}
//    ]

    public static void main(String[] args) {
        TreeBuilder.Node node1 = new TreeBuilder.Node("1","-1","商品目录");
        TreeBuilder.Node node2 = new TreeBuilder.Node("11","1","日用品");
        TreeBuilder.Node node3 = new TreeBuilder.Node("12","1","食品");
        TreeBuilder.Node node4 = new TreeBuilder.Node("111","11","洗发水");
        TreeBuilder.Node node5 = new TreeBuilder.Node("1111","111","霸王");
        TreeBuilder.Node node6 = new TreeBuilder.Node("22","-1","中国");
        TreeBuilder.Node node7 = new TreeBuilder.Node("33","22","成都");
        // 获取全部目录节点
        List<TreeBuilder.Node> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);
        nodes.add(node6);
        nodes.add(node7);

        // 拼装树形json字符串
        String json = new TreeBuilder().buildTree(nodes);
        System.out.println(json);
    }

}

class TreeBuilder {

    List<Node> nodes = new ArrayList<>();

    public String buildTree(List<Node> nodes) {

        TreeBuilder treeBuilder = new TreeBuilder(nodes);
        return treeBuilder.buildJSONTree();
    }

    public TreeBuilder() {
    }

    public TreeBuilder(List<Node> nodes) {
        super();
        this.nodes = nodes;
    }

    // 构建JSON树形结构
    public String buildJSONTree() {
        List<Node> nodeTree = buildTree();

        JSONArray jsonArray = new JSONArray();
        for(Node node : nodeTree){
            JSONObject jo = new JSONObject();
            jo.put("id", node.getId());
            jo.put("pid", node.getPid());
            jo.put("name", node.getName());
            jo.put("children",node.getChildren());
            jsonArray.add(jo);
        }
        return jsonArray.toString();
    }

    // 构建树形结构
    public List<Node> buildTree() {
        List<Node> treeNodes = new ArrayList<>();
        List<Node> rootNodes = getRootNodes();
        for (Node rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    // 递归子节点
    public void buildChildNodes(Node node) {
        List<Node> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (Node child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);
        }
    }

    // 获取父节点下所有的子节点
    public List<Node> getChildNodes(Node pnode) {
        List<Node> childNodes = new ArrayList<>();
        for (Node n : nodes) {
            if (pnode.getId().equals(n.getPid())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }

    // 判断是否为根节点
    public boolean rootNode(Node node) {
        boolean isRootNode = true;
        for (Node n : nodes) {
            if (node.getPid().equals(n.getId())) {
                isRootNode = false;
                break;
            }
        }
        return isRootNode;
    }

    // 获取集合中所有的根节点
    public List<Node> getRootNodes() {
        List<Node> rootNodes = new ArrayList<>();
        for (Node n : nodes) {
            if (rootNode(n)) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }

    public static class Node {

        private String id;
        private String pid;
        private String name;
        private List<Node> children;

        public Node() {
        }

        public Node(String id, String pid, String name) {
            super();
            this.id = id;
            this.pid = pid;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public List<Node> getChildren() {
            return children;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }
    }
}