import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//todo sijashika
class TrappingRainWater {
    public static void main(String[] args) {
        int[] h = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}; // 6
//        int[] h = new int[]{4,2,0,3,2,5}; // 9
        //int[] h = new int[]{4,2,3}; // 1

        //drawContainer(h);
        System.out.println("Water trapped is " + trapO1Space(h));
        System.out.println("Expected is " + trapOnSpace(h));
    }

    public static int trapOnSpace(int[] h) {
        // Min(l,r) - currentHeight -> boundary , water will be contained by minimum height of block
        int units = 0;

        int[] maxL = new int[h.length];
        int[] maxR = new int[h.length];
        int[] min = new int[h.length];

        int maxLeft = 0;
        int maxRight = 0;

        for (int i = 0; i < h.length; i++) {
            maxLeft = Math.max(maxLeft, i == 0 ? 0 : h[i - 1]);
            maxL[i] = maxLeft;
        }

        for (int i = h.length - 1; i >= 0; i--) {
            maxRight = Math.max(maxRight, i == h.length - 1 ? 0 : h[i + 1]);
            maxR[i] = maxRight;
        }

        for (int i = 0; i < h.length; i++) {
            min[i] = Math.min(maxL[i], maxR[i]);
        }


        for (int i = 0; i < h.length; i++) {
            int n = h[i];
            int water = min[i] - n;
            water = water < 1 ? 0 : water;

            //Water unit in position i Min(l,r) - currentHeight
            units += water;
        }


        return units;
    }

    public static int trapO1Space(int[] h) {
        if (h.length == 0) return 0;

        int units = 0;
        int water;

        int left = 0;
        int right = h.length - 1;

        int maxLeft = h[left];
        int maxRight = h[right];

        while (left < right) {
            //shift smaller pointer
            if (maxRight < maxLeft) {
                right--;
                maxRight = Math.max(maxRight, h[right]);
                water = Math.min(maxLeft, maxRight) - h[right];
            } else {
                left++;
                maxLeft = Math.max(maxLeft, h[left]);
                water = Math.min(maxLeft, maxRight) - h[left];
            }
            units += water < 1 ? 0 : water;

        }


        return units;
    }

    public static int trapOld(int[] h) {
        int units = 0;
        int width = 1;
        boolean collect = false;
        Integer leftWall = null;
        Integer rightWall = null;


        for (int i = 0; i < h.length; i++) {
            if (i == h.length - 1 && rightWall == null) continue;


            //reached right wall
            if (rightWall != null && rightWall == i) {
                collect = false;
                int maxWaterHeight = Math.min(h[leftWall], h[rightWall]);
                for (int j = leftWall + 1; j < rightWall; j++) {
                    //times I need to check
                    int waterHeight = maxWaterHeight - h[j];
                    int thisArea = waterHeight * width;
                    units += thisArea;
                }
                rightWall = null;
                leftWall = null;
            }

            //look for wall boundaries
            if (!collect) {
                int[] walls = findLeftWall(i, h);
                if (walls.length == 0) {
                    continue;
                }
                collect = true;
                leftWall = walls[0];
                rightWall = walls[1];
                System.out.println("Left wall is " + leftWall);
                System.out.println("Right wall is " + rightWall);
            }

            /*
                        if(nextHeight != null && height > nextHeight && !collect) {
                            findLeftWall(i,h,leftWall,rightWall);
                            collect = leftWall != null;
                            if(collect) rightWall = null;
                            continue;
                        }

                        if(nextHeight == null || nextHeight > height) {

                            if(nextHeight != null &&  leftWall == null) {
                                findLeftWall(i, h, leftWall, rightWall);
                                collect = leftWall != null;
                                if(collect) rightWall = null;
                                continue;
                            }

                            if(h[leftWall] <= nextHeight && collect) {

                            }

                            System.out.println("==============================");
                        }*/
        }

        return units;
    }

    private static int[] findLeftWall(int current, int[] h) {
        int[] result = new int[]{};
        int possibleLeftWall = h[current];
        int leftWallIndex = current;
        if (current + 1 == h.length) return result;
        int possibleWaterStart = h[current + 1];
        LinkedList<Integer> rightWalls = new LinkedList<>();

        //early exit
        if (possibleWaterStart > possibleLeftWall || possibleLeftWall == 0) return result;
        int waterStartIndex = current + 1;

        //look for right wall
        //j is water initially
        for (int j = current + 1; j < h.length; j++) {

            //after water
            int k = j + 1;
            if (k == h.length) break;
            int possibleRightWall = h[k];
            int possibleRightWallIndex = k;

            //invalidate possible right wall
            if (possibleRightWall < possibleLeftWall && possibleWaterStart > possibleRightWall) continue;
            if (possibleRightWall == 0) continue;
            if (possibleRightWall == waterStartIndex) {
                //left wall must be equal / smaller than right
                if (!(possibleLeftWall <= possibleRightWall)) continue;
            } else if (possibleLeftWall == waterStartIndex) {
                //right wall must be equal / bigger than left
                if (!(possibleRightWall >= possibleLeftWall)) continue;
            } else {
                if (!(possibleRightWall > possibleWaterStart) || !(possibleLeftWall > possibleWaterStart)) continue;
            }

            if (possibleRightWall > 0 && possibleLeftWall > 0) {
                rightWalls.add(k);
            }
        }


        int max = 0;
        Integer index = null;
        for (int w = 0; w < rightWalls.size(); w++) {
            int i = h[w];
            //System.out.println("Options are index : "+w+ " value : "+i + " water is "+possibleWaterStart);
            System.out.println("Left Option : index " + current + " and value : " + h[current]);
            System.out.println("Right Option : index " + w + " and value : " + i);
            System.out.println("Water Level should be " + Math.min(h[current], h[w]) + "\n");
            /*if(w != rightWalls.size() - 1) {
                if(h[w + 1] < )
            }*/
            if (max < i) {
                max = i;
                index = w;
            }
        }
        return index != null ? new int[]{current, index} : result;
    }

    private static void drawContainer(int[] h) {
        HashMap<Integer, List<Integer>> heights = new HashMap<>();
        int maxHeight = 0;

        for (int i = 0; i < h.length; i++) {
            int height = h[i];
            maxHeight = Math.max(height, maxHeight);
            List<Integer> list = heights.getOrDefault(height, new ArrayList<>());
            list.add(i);
            heights.put(height, list);
        }


        String found = "*";
        String Notfound = " ";
        String ground = "_";
        StringBuffer b;
        for (int current = maxHeight; current >= 0; current--) {
            b = new StringBuffer();
            List<Integer> present = heights.getOrDefault(current, new ArrayList<>());
            for (int i = 0; i < h.length; i++) {
                if (current == 0) {
                    b.append(ground);
                    continue;
                }
                b.append(present.contains(i) ? found : Notfound);
            }
            System.out.println(b);

            //System.out.println("\n");
        }


    }

}
