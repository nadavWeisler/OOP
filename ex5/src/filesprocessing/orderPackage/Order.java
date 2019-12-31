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
        ArrayList<File> result = new ArrayList<File>();
        if (Utils.ABS_ORDER.equals(this.orderType)) {
            result = this.AbsOrder(filesList);
        } else if (Utils.SIZE_ORDER.equals(this.orderType)) {
            result = this.SizeOrder(filesList);
        } else if (Utils.TYPE_ORDER.equals(this.orderType)) {
            result = this.TypeOrder(filesList);
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
    private ArrayList<File> GetOrderedByAbsolutePath(ArrayList<File> files) {
        ArrayList<File> result = new ArrayList<File>();
        HashMap<String, File> hashMap = new HashMap<String, File>();
        for (File file :
                files) {
            hashMap.put(file.getAbsolutePath(), file);
        }
        String[] sorted = Arrays.copyOf(hashMap.keySet().toArray(),
                hashMap.keySet().size(), String[].class);
        Utils.BubbleSortStringArray(sorted);

        for (String fileName : sorted) {
            result.add(hashMap.get(fileName));
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
        HashMap<String, ArrayList<File>> result = new HashMap<String, ArrayList<File>>();
        for (File file : files) {
            String ext = Utils.getFileExtension(file.getName());
            ArrayList<File> toAdd = new ArrayList<File>();
            toAdd.add(file);
            if (result.containsKey(ext)) {
                toAdd.addAll(result.get(ext));
            }
            result.put(ext, toAdd);
        }

        for (String type : result.keySet()) {
            result.put(type, this.GetOrderedByAbsolutePath(result.get(type)));
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
        HashMap<Long, ArrayList<File>> result = new HashMap<Long, ArrayList<File>>();
        for (File file : files) {
            ArrayList<File> toAdd = new ArrayList<File>();
            toAdd.add(file);
            if (result.containsKey(file.length())) {
                toAdd.addAll(result.get(file.length()));
            }
            result.put(file.length(), toAdd);
        }

        for (Long size : result.keySet()) {
            result.put(size, this.GetOrderedByAbsolutePath(result.get(size)));
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
        return this.GetOrderedByAbsolutePath(files);
    }

    /**
     * Order by type
     *
     * @param files Files to order
     * @return Ordered Array List
     */
    private ArrayList<File> TypeOrder(ArrayList<File> files) {
        HashMap<String, ArrayList<File>> allTypes = this.GetFilesHashTypes(files);
        ArrayList<File> result = new ArrayList<File>();
        String[] sortedArray = Arrays.copyOf(allTypes.keySet().toArray(),
                allTypes.keySet().size(), String[].class);
        ;
        Utils.BubbleSortStringArray(sortedArray);
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
        ArrayList<File> result = new ArrayList<File>();
        HashMap<Long, ArrayList<File>> allFilesBySize = this.GetFilesHashSize(files);
        Long[] sortedArray = Arrays.copyOf(allFilesBySize.keySet().toArray(), allFilesBySize.keySet().size(),
                Long[].class);
        Utils.longBubbleSort(sortedArray);
        for (Long size : sortedArray) {
            result.addAll(allFilesBySize.get(size));
        }
        return result;
    }
}
