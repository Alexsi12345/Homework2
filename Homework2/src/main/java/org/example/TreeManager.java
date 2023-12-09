package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeManager {
    private final DatabaseConnection dbConnection;

    public TreeManager(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Tree> readTrees() {
        List<Tree> trees = new ArrayList<>();

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM TREES")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int parentId = resultSet.getInt("parent_id");
                trees.add(new Tree(id, parentId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trees;
    }

    public int getTotalLeavesCount(List<Tree> trees) {
        Map<Integer, List<Integer>> treeMap = buildTreeMap(trees);
        int totalLeavesCount = 0;

        for (Tree tree : trees) {
            if (!treeMap.containsKey(tree.getId())) {
                totalLeavesCount++;
            }
        }

        return totalLeavesCount;
    }

    private Map<Integer, List<Integer>> buildTreeMap(List<Tree> trees) {
        Map<Integer, List<Integer>> treeMap = new HashMap<>();

        for (Tree tree : trees) {
            int parentId = tree.getParentId();
            treeMap.putIfAbsent(parentId, new ArrayList<>());
            treeMap.get(parentId).add(tree.getId());
        }

        return treeMap;
    }

    public static void main(String[] args) {
        // Используйте H2 соединение
        DatabaseConnection h2Connection = new H2Connection();
        TreeManager treeManager = new TreeManager(h2Connection);

        List<Tree> trees = treeManager.readTrees();
        int totalLeavesCount = treeManager.getTotalLeavesCount(trees);

        System.out.println("Total Leaves Count: " + totalLeavesCount);

        // Сохранение результата в файл output.csv
        CsvWriter.writeToCsv("output.csv", totalLeavesCount);

        // Отключение от базы данных
        try {
            h2Connection.disconnect(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



