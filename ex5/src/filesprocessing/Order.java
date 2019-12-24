package filesprocessing;

import java.io.File;
import java.util.*;

public class Order {

    public ArrayList<String> OrderList(String orderType, boolean reverse) {
        ArrayList<String> result = new ArrayList<>();
        switch (orderType) {
            case "abs":
                break;
            case "size":
                break;
            case "type":
                break;
        }

        if (reverse) {
            Collections.reverse(result);
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
            String ext = Utils.GetFileExtension(file.getName());
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
        LinkedHashSet<String> sortedSet = new LinkedHashSet<String>(allNames.keySet());
        ArrayList<File> result = new ArrayList<>();
        for (String name :
                sortedSet) {
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
        LinkedHashSet<String> sortedSet = new LinkedHashSet<String>(allTypes.keySet());
        for (String type :
                sortedSet) {
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
        LinkedHashSet<Long> sortedSet = new LinkedHashSet<>(allFilesBySize.keySet());
        for (Long size :
                sortedSet) {
            result.addAll(allFilesBySize.get(size));
        }
        return result;
    }
}
