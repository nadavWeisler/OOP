package filesprocessing.orderPackage;

import filesprocessing.Utils;

import java.io.File;
import java.util.*;

public class Order {
    private String orderType;
    private boolean reverse;

    /**
     * Constructor
     *
     * @param orderType Order type
     * @param reverse   Reverse
     */
    public Order(String orderType, boolean reverse) {
        this.orderType = orderType;
        this.reverse = reverse;
    }

    /**
     * Order list
     *
     * @param filesList Files list
     * @return Ordered file array list
     */
    public ArrayList<File> OrderList(ArrayList<File> filesList) {
        ArrayList<File> result = new ArrayList<>();
        switch (this.orderType) {
            case Utils.ABS_ORDER:
                result = this.AbsOrder(filesList);
                break;
            case Utils.SIZE_ORDER:
                result = this.SizeOrder(filesList);
                break;
            case Utils.TYPE_ORDER:
                result = this.TypeOrder(filesList);
                break;
        }

        if (this.reverse) {
            Utils.reverseFilesArrayList(result);
        }

        return result;
    }

    /**
     * Get files hash map with there absolute path as a key
     *
     * @param files Files to order
     * @return Hash map
     */
    private HashMap<String, File> GetAbsolutePathList(ArrayList<File> files) {
        HashMap<String, File> result = new HashMap<>();
        for (File file :
                files) {
            result.put(file.getAbsolutePath(), file);
        }
        return result;
    }

    /**
     * Get hash map group by types
     *
     * @param files Files to group
     * @return Hash map group by file type
     */
    private HashMap<String, ArrayList<File>> GetFilesHashTypes(ArrayList<File> files) {
        HashMap<String, ArrayList<File>> result = new HashMap<>();
        for (File file :
                files) {
            String ext = Utils.getFileExtension(file.getName());
            ArrayList<File> toAdd = new ArrayList<>();
            toAdd.add(file);
            if (result.containsKey(ext)) {
                toAdd.addAll(result.get(ext));
            }
            result.put(ext, toAdd);
        }
        return result;
    }

    /**
     * Get hash map group by size
     *
     * @param files Files
     * @return Hash map group by size
     */
    private HashMap<Long, ArrayList<File>> GetFilesHashSize(ArrayList<File> files) {
        HashMap<Long, ArrayList<File>> result = new HashMap<>();
        for (File file :
                files) {
            ArrayList<File> toAdd = new ArrayList<>();
            toAdd.add(file);
            if (result.containsKey(file.length())) {
                toAdd.addAll(result.get(file.length()));
            }
            result.put(file.length(), toAdd);
        }
        return result;
    }

    /**
     * Order by file name
     *
     * @param files Files to order
     * @return Ordered array list
     */
    private ArrayList<File> AbsOrder(ArrayList<File> files) {
        HashMap<String, File> allNames = this.GetAbsolutePathList(files);
        String[] sortedArray = (String[]) allNames.keySet().toArray();
        Utils.sortStringArray(sortedArray);
        ArrayList<File> result = new ArrayList<>();
        for (String name : sortedArray) {
            result.add(allNames.get(name));
        }
        return result;
    }

    /**
     * Order by type
     *
     * @param files Files to order
     * @return Ordered Array List
     */
    private ArrayList<File> TypeOrder(ArrayList<File> files) {
        HashMap<String, ArrayList<File>> allTypes = this.GetFilesHashTypes(files);
        ArrayList<File> result = new ArrayList<>();
        String[] sortedArray = (String[]) allTypes.keySet().toArray();
        Utils.sortStringArray(sortedArray);
        for (String type : sortedArray) {
            result.addAll(allTypes.get(type));
        }
        return result;
    }

    /**
     * Order by size
     *
     * @param files Files to order
     * @return Ordered array list
     */
    private ArrayList<File> SizeOrder(ArrayList<File> files) {
        ArrayList<File> result = new ArrayList<>();
        HashMap<Long, ArrayList<File>> allFilesBySize = this.GetFilesHashSize(files);
        Long[] sortedArray = (Long[]) allFilesBySize.keySet().toArray();
        Utils.longBubbleSort(sortedArray);
        for (Long size : sortedArray) {
            result.addAll(allFilesBySize.get(size));
        }
        return result;
    }
}
