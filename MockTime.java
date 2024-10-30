class MockTime {
    public static void main(String[] args) {

        String time = "?7:??";

        System.out.println("Output is " + getCombinations(time));
        System.out.println("Expected is " + getTimeCombinations(time));
    }

    private static int getTimeCombinations(String time) {
        if (!time.contains("?")) return 0;
        int result = 1;
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) != '?') continue;

            switch (i) {
                //hr 1
                //max 4
                case 0 -> {
                    if (time.charAt(1) == '?') {
                        //??:00
                        result = 24;
                        i++;
                    } else {
                        result *= (Integer.parseInt(String.valueOf(time.charAt(1))) > 3) ? 2 : 3;
                    }
                }

                //hr 2
                case 1 -> //1 is not ?
                        result *= time.charAt(0) != '2' ? 10 : 4;

                //min 1
                case 3 -> result *= 6;

                //min 2
                case 4 -> result *= 10;
            }
        }
        return result;
    }

    public static int getCombinations(String time) {
        //time is valid
        //if no combinations return 0
        //always 5 char

        /*
        (Min) 00:00 - 23:59 (Max)

        1. “00:00” -> 0

        2. “00:??” -> 60

        3. “?0:00” -> 3
z
        4. “??:00” -> 24

        5. “17:5?” -> 10

        6. “??:??” -> 1440

        7. "?7:??" ->

        */


        //initialize variables for loop //condition to do loop //at end of the loop
        //time.length = 5;
        //{'?','?',':','2','5'}

        //Hh:Mm
        char H = time.charAt(0);
        char h = time.charAt(1);

        char M = time.charAt(3);
        char m = time.charAt(4);

        int totalHourCombinations = 0;
        int totalMinuteCombinations = 0;


        //H Conditions
        // if H != ?
        //if H == 2
        // h < 4
        // h < 10

        //h Conditions
        //if h != ?
        // h <= 3
        //H <= 2
        // h > 3
        // H <= 1

        // M Conditions
        // if M == ?
        //permutation = 6

        //m Conditions
        // if m == ?
        //permutation = 9


        //Test Hours section
        if (H != '?' && h == '?') {
            if (H == '2') {
                totalHourCombinations += 4;
            } else if (H == '0' || H == '1') {
                totalHourCombinations += 10;
            }
        } else if (H == '?' && h != '?') {
            System.out.println("Small h is: " + h);
            if (Integer.parseInt(String.valueOf(h)) > 3) {
                totalHourCombinations += 2;
            } else {
                totalHourCombinations += 3;
            }
        } else if (H == '?' && h == '?') {
            totalHourCombinations += 24;
        }

        //Test Minutes Section
        if (M != '?' && m == '?') {
            totalMinuteCombinations += 10;
        } else if (M == '?' && m != '?') {
            totalMinuteCombinations += 6;
        } else if (M == '?' && m == '?') {
            totalMinuteCombinations += 60;
        }

        //Check if a value is 0
        if (totalMinuteCombinations == 0 || totalHourCombinations == 0) {
            if (totalMinuteCombinations == 0) {
                ++totalMinuteCombinations;
            } else {
                ++totalHourCombinations;
            }
        }

        return totalHourCombinations * totalMinuteCombinations;
    }

}
