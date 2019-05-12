/*
 * Create a hash table using linear pobing
 * @author Kaitian Li
 * 3/23/2019
 */
package linearprobinghashtable;

public class LinearProbingHashTable<K, V> {
    private transient int count = 0;
    private transient Entry<K,V> table[];
    //initial the table size as 11
    public LinearProbingHashTable(){
        table = new Entry[11];
    }
    //custom the table size, make sure the number is a prime number
    public LinearProbingHashTable(int num){
        if(num <= 1){
            throw new IllegalArgumentException("Invalid size");
        }
        table = new Entry[nextPrime(num)];
    }
    /*
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    @SuppressWarnings("empty-statement")
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;
        for( ; !isPrime( n ); n += 2 )
            ;
        return n;
    }
    /*
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;
        if( n == 1 || n % 2 == 0 )
            return false;
        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;
        return true;
    }
    //insert method that return true if insert success, else return false
    public boolean insert(K key, V value){
        //if entry key is null, throw exception
        if(key == null){
            throw new NullPointerException("Invalid key");
        }
        if(value == null){
            throw new NullPointerException("Invalid value");
        }
        //if table size is half full, rehash the table
        if(count >= table.length/2){
            rehash();
        }
        int hash = key.hashCode();//set hash code
        int index = hash % table.length;//find the insert location
        //if insert position is null, put the key and value into table
        if(table[index] == null){
            table[index] = new Entry(key, value);
            count += 1;//calculate the size
            return true;
        }
        //find the empty location by using linear probing
        while(table[index] != null){
            //if the index's value has been deleted
            if(table[index].isDeleted){
                //entry the new key and value
                table[index] = new Entry(key, value);
                count ++;
                return true;
            }
            //check the duplicate key
            if(table[index].key.equals(key)){
                //if the value has been deleted
                // break the loop and insert the value into this location
                if(table[index].isDeleted){
                    //entry the new key and value
                    table[index] = new Entry(key, value);
                    count ++;
                    return true;
                }
                //return flase since table has duplicate key
                else{
                    return false;
                }
            }
            //liner probing
            index = (index+1)%table.length;
        }
        //entry the new key and value and return true
        table[index] = new Entry(key, value);
        count ++;
        return true;
    }
    //method that return the value of the insert key
    public V find(K key){
        //throw exception if entry key is null
        if(key == null){
             throw new NullPointerException("Invalid key");
        }
        //get the index
        int index = key.hashCode() % table.length;
        //loop that check the location
        while(table[index] != null){
            //find the key and the value do not have the mark deleted
            if(table[index].key.equals(key) && !table[index].isDeleted){
                return table[index].value;
            }
            //liner probing
            index = (index+1) % table.length;
        }
        //return null since cannot find the key
        return null;
    }
    //delete method that mark the value of deleted
    public boolean delete(K key){
         //throw exception if key is null
        if(key == null){
             throw new NullPointerException("Invalid key");
        }
        //set the index
        int index = key.hashCode()%table.length;
        //check all the poosible locations by using linear probing
        while(table[index] != null){
            //find the key, mark the value with deleted and re insert it to the table
            if(table[index].key.equals(key)){ 
                table[index].isDeleted = true;
                count --;
                return true;
            }
            index = (index+1)%table.length;
        }
        //return flase if not find
        return false;
    }
    //rehash function that double the table size, if table size is not prime
    //using next prime to creat a prime number size table
    //ignore all the marked deleted entry
    private  void rehash(){
        int oldSize = table.length;
        int size = nextPrime(oldSize * 2);
        count = 0;//initial count of the table
        Entry<K, V> oldTable[] = table;//a temp table to store old table data
        table = new Entry[size];//initial table
        //for loop to store all needs items into new table
        //by rehash key with new table size
        for(int i = 0; i<oldSize; i++){
            if(oldTable[i] != null){
                if(!oldTable[i].isDeleted){
                    int index = oldTable[i].key.hashCode() % table.length;
                    while(table[index] != null){
                        index = (index+1) % table.length;
                    }
                    table[index] = new Entry(oldTable[i].key, oldTable[i].value);
                    count ++;
                }
            }
        }
    }
    //get the hash value of the key
    public int getHashValue(K key){
        //throw exception if input key is null
        if(key == null){
            throw new NullPointerException("Invalid key");
        }
        //return the hash value
        else{
            return key.hashCode()%table.length;
        }
    }
    //return the location for the given key
    //-1 if not found
    public int getLocation(K key){
        //throw exception if input key is null
        if(key == null){
            throw new NullPointerException("Invalid key");
        }
        else{
            //set index
            int index = key.hashCode()%table.length;
            //if the entry of index is null return -1,
            //if the find the entry key, return the index
            while(table[index] != null){
                if(table[index].key.equals(key)){
                    return index;
                }
                index = (index+1)%table.length;
            }
            return -1;
        }
    }
    //override the toString function
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(); 
        for(int i = 0; i < table.length; i++){
            if(table[i] == null){
                sb.append(i).append("\n");
            }
            else if(table[i].isDeleted){
                sb.append(i).append("\t").append(table[i].key)
                        .append(",").append("\t").append(table[i].value)
                        .append("\t").append("deleted").append("\n");
            }
            else{
                sb.append(i).append("\t").append(table[i].key)
                        .append(",").append("\t").append(table[i].value).append("\n");
            }
        }
        return new String(sb);
    }
    //class for put the key and value
    private static class Entry<K, V>{
        private K key;
        private V value;
        protected boolean isDeleted;
        protected Entry(K key, V value){
            this.key = key;
            this.value = value;
            isDeleted = false;
        }
    }
    
    public static void main(String[] args) {
        //user can intial the table size
        LinearProbingHashTable<Integer, String> hashTable = new LinearProbingHashTable<>(7);
        hashTable.insert(0, "test 1");//inster key 0 with value test 1
        hashTable.insert(2, "test 1");//insert key 2 with value test 1
        System.out.println("insert duplicate key: "+ hashTable.insert(0, "test 2"));//test insert duplicate
        System.out.println("delete key 2: "+hashTable.delete(2));
        System.out.println("return the value of key 2: "+hashTable.find(2));//should return null since it has been deleted
        System.out.println("insert key 2 again: " + hashTable.insert(2, "test 2"));
        //print the first table view
        System.out.println(hashTable);
        System.out.println("insert new key and rehash the table");
        //insert three new keys to over half size of 7 and testing the linear probing hash
        hashTable.insert(1, "test 2");//insert position that been deleted
        hashTable.insert(18, "test 1");
        hashTable.insert(17, "test 1");//test linear probing
        System.out.println("the hash value of key 17: " + hashTable.getHashValue(17));//return 0
        System.out.println("the location of key 17 after linear probing: " + hashTable.getLocation(17));//return 4
        System.out.println(hashTable);
    }
    
}
