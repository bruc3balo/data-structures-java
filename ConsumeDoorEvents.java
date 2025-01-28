public class ConsumeDoorEvents {
    public static String consumeDoorEvents(String events) {
        boolean directionOpening = false;
        boolean isPaused = true;
        int doorPosition = 0;

        final StringBuilder sb = new StringBuilder();

        for (Character c : events.toCharArray()) {
            switch (c) {
                case '.':
                    if (isPaused) break;

                    if (directionOpening) doorPosition++;
                    else doorPosition--;

                    isPaused = switch (doorPosition) {
                        case 5, 0 -> true;
                        default -> false;
                    };
                    break;

                case 'P':
                    isPaused = !isPaused;
                    if (isPaused) break;

                    switch (doorPosition) {

                        case 0:
                            //If door was closed, start opening
                            directionOpening = true;
                            doorPosition++;
                            break;

                        case 5:
                            //If door was open, start closing
                            directionOpening = false;
                            doorPosition--;
                            break;

                        default:
                            if (directionOpening) doorPosition++;
                            else doorPosition--;
                            break;

                    }
                    break;

                case 'O':
                    //If it was opening, close
                    if (directionOpening) doorPosition--;

                        //If it was closing, open
                    else doorPosition++;

                    switch (doorPosition) {
                        case 0:
                        case 5:
                            isPaused = true;
                            break;
                        default:
                            break;
                    }

                    directionOpening = !directionOpening;
                    break;
            }
            sb.append(doorPosition);
        }

        return sb.toString();
    }
}

