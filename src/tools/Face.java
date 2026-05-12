package tools;

public class Face {

    public int[] vertexIdx;
    public int[] normalIdx;

    public int smoothingGroup;
    public boolean hasNormals;

    public Face(int[] vertexIdx, int[] normalIdx, int smoothingGroup, boolean hasNormals) {
        this.vertexIdx = vertexIdx;
        this.normalIdx = normalIdx;
        this.smoothingGroup = smoothingGroup;
        this.hasNormals = hasNormals;
    }
}
