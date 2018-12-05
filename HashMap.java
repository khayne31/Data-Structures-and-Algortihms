import java.util.List;
import java.util.Set;

/**
 * Your implementation of HashMap.
 *
 * @userid khaynes31
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);

    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If anoxther entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {

        if (key == null || value == null) {
            throw new IllegalArgumentException("either the "
                    + "data or the key is null, please give a non nul value");
        }
        float load = (float) (size + 1) / (float) (table.length);
        if (load > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int index = Math.abs(key.hashCode() % table.length);
        int deletedIndex = -1;
        boolean found = false;
        int increment = 0;
        while (!found) {
            found = increment >= table.length;
            if (table[index] == null) {
                if (deletedIndex != -1) {
                    table[deletedIndex] = new MapEntry<>(key, value);
                    size++;
                } else {
                    table[index] = new MapEntry<>(key, value);
                    found = !found;
                    size++;
                }
                return null;
            } else if (table[index].getKey().equals(key)) {
                V returnValue = table[index].getValue();
                if (table[index].isRemoved()) {
                    table[index] = new MapEntry<>(key, value);
                    size++;
                } else {
                    table[index].setValue(value);
                }

                return returnValue;
            } else if (table[index].isRemoved()) {
                if (deletedIndex == -1) {
                    deletedIndex = index;
                }
            }
            if (found) {
                if (deletedIndex != -1) {
                    table[deletedIndex] = new MapEntry<>(key, value);
                    size++;
                }
            }
            index = (index + 1) % table.length;
            increment++;

        }
        return null;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the given key value is null");
        }
        int index = key.hashCode() % table.length;
        V returnvalue = null;
        if (table[index] == null) {
            throw new java.util.NoSuchElementException("the element "
                    + "associated with the given key"
                    + " does not exist in this hash table");
        } else {
            //returnvalue  = table[index].getValue();
            //table[index].setRemoved(true);

            for (int i = 0; i < table.length; i++) {
                if (table[(index + i) % table.length] == null) {
                    throw new java.util.NoSuchElementException("the element "
                            + "associated with the given key "
                            + "does not exist in this hash table");
                } else if (table[(index + i) % table.length]
                        .getKey().equals(key)) {
                    if (table[(index + i) % table.length].isRemoved()) {
                        throw new java.util.NoSuchElementException("the element"
                                + " associated with the given key does "
                                + "not exist in this hash table");
                    }
                    returnvalue = table[(index + i) % table.length].getValue();
                    table[(index + i) % table.length].setRemoved(true);
                    break;
                }
            }

        }
        size--;
        return returnvalue;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key "
                   + "given is null. please give a non null key");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (size == 0) {
            throw new java.util.NoSuchElementException("the element "
                    + "associated with the given "
                    + "key does not exist in this hash table");
        } else {
            for (int i = 0; i < table.length; i++) {
                if (table[(index + i) % table.length] == null) {
                    throw new java.util.NoSuchElementException("the element "
                            + "associated with the"
                            + " given key does not exist in this hash table");
                } else if (table[(index + i) % table.length]
                        .getKey().equals(key)) {
                    return table[(index + i) % table.length].getValue();
                }
            }
            return null;
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the given key is null,"
                    + " please give a non null key");
        }

        int startindex = key.hashCode() % table.length;
        if (table[startindex] == null) {
            return false;
        } else {
            for (int i = 0; i < table.length; i++) {
                if (table[(startindex + i) % table.length] != null) {
                    if (table[(startindex + i) % table.length]
                            .getKey().equals(key)
                            && !table[(startindex + i) % table.length]
                            .isRemoved()) {
                        return true;
                    }
                }

            }
            return false;
        }

    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        java.util.HashSet<K> returnset =  new java.util.HashSet<>();
        for (MapEntry<K, V> item: table) {
            if (item != null) {
                if (!item.isRemoved()) {
                    returnset.add(item.getKey());
                }
            }
        }
        return returnset;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add 
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        java.util.LinkedList<V> returnList = new java.util.LinkedList<>();
        for (MapEntry<K, V> item: table) {
            if (item != null) {
                if (!item.isRemoved()) {
                    returnList.add(item.getValue());
                }
            }
        }
        return returnList;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the number of
     * items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("the length "
                   + "you gave is less than the items in the list");
        }
        MapEntry<K, V>[] temp = (MapEntry<K, V>[]) new MapEntry[length];
        for (MapEntry<K, V> item: table) {
            if (item != null) {
                if (!item.isRemoved()) {
                    int index = Math.abs(item.getKey().
                            hashCode() % temp.length);
                    while (temp[index] != null) {
                        index++;
                    }
                    temp[index % temp.length] = item;
                }

            }
        }
        table = temp;
    }

    /**
     * Clears the table and resets it to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }
    
    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}
