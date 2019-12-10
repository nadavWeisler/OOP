public abstract class SimpleHashSet implements SimpleSet {
    /**
     * Describes the higher load factor of a newly created hash set
     */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;

    /**
     * Describes the lower load factor of a newly created hash set
     */
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;

    /**
     * Describes the capacity of a newly created hash set
     */
    protected static final int INITIAL_CAPACITY = 16;

    /**
     *
     */
    protected float upperLoadFactor;

    /**
     *
     */
    protected float lowerLoadFactor;

    /**
     * Constructs a new hash set with the default capacities given
     * in DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY
     */
    SimpleHashSet() {
        this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     *
     * @param upperLoadFactor The upper load factor before rehashing
     * @param lowerLoadFactor The lower load factor before rehashing
     */
    SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    /**
     * Get capacity
     *
     * @return The current capacity (number of cells) of the table.
     */
    abstract int capacity();

    /**
     * Clamps hashing indices to fit within the current table capacity
     *
     * @param index The index before clamping
     * @return An index properly clamped
     */
    abstract int clamp(int index);

    /**
     * Get lower load factor
     *
     * @return The lower load factor of the table
     */
    protected float getLowerLoadFactor() {
        return this.lowerLoadFactor;
    }

    /**
     * Get upper load factor
     * @return The higher load factor of the table
     */
    protected float getUpperLoadFactor() {
        return this.upperLoadFactor;
    }
}
