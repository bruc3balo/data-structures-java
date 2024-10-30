import java.util.*;

import static java.util.Comparator.comparingInt;

class MinWin {
    public static void main(String[] args) {

//        String s = "obzcopzocynyrsgsarijyxnkpnukkrvzuwdjldxndmnvevpgmxrmvfwkutwekrffnloyqnntbdohyfqndhzyoykiripdzwiojyoznbtogjyfpouuxvumtewmmnqnkadvzrvouqfbbdiqremqzgevkbhyoznacqwbhtrcjwfkzpdstpjswnpiqxjhywjanhdwavajrhwtwzlrqwmombxcaijzevbtcfsdcuovckoalcseaesmhrrizcjgxkbartdtotpsefsrjmvksqyahpijsrppdqpvmuocofuunonybjivbjviyftsyiicbzxnwnrmvlgkzticetyfcvqcbjvbufdxgcmesdqnowzpshuwcseenwjqhgsdlxatamysrohfnixfprdsljyyfhrnnjsagtuihuczilgvtfcjwgdhpbixlzmakebszxbhrdibpoxiwztshwczamwnninzmqrmpsviydkptjzpktksrortapgpxwojofxeasoyvyprjoguhqobehugwdvtzlenrcttuitsiijswpogicjolfxhiscjggzzissfcnxnvgftxvbfzkukqrtalvktdjsodmtgzqtuyaqvvrbuexgwqzwduixzrpnvegddyyywaquxjxrnuzlmyipuqotkghfkpknqinoidifnfyczzonxydtqroazxhjnrxfbmtlqcsfhshjrxwqvblovaouxwempdrrplefnxmwrwfjtebrfnfanvvmtbzjesctdgbsfnpxlwihalyiafincfcwgdfkvhebphtxukwgjgplrntsuchyjjuqozakiglangxkttsczhnswjksnuqwflmumpexxrznzwxurrysaokwxxqkrggytvsgkyfjrewrcvntomnoazmzycjrjrqemimyhriyxgrzcfuqtjhvjtuhwfzhwpljzajitrhryaqchnuawbxhxrpvyqcvhpggrpplhychyulijhkglinibedauhvdydkqszdbzfkzbvhldstocgydnbfjkcnkfxcyyfbzmmyojgzmasccaahpdnzproaxnexnkamwmkmwslksfpwirexxtymkmojztgmfhydvlqtddewjvsrmyqjrpycbmndhupmdqqabiuelacuvxnhxgtpvrtwfgzpcrbhhtikbcqpctlxszgpfbgcsbaaiapmtsucocmpecgixshrrnhyrpalralbccnxvjzjllarqhznzghswqsnfuyywmzbopyjyauknxddgdthlabjqtwxpxwljvoxkpjjpfvccyikbbrpdsyvlxscuoofkecwtnfkvcnzbxkeabtdusyhrqklhaqreupakxkfzxgawqfwsaboszvlshwzhosojjotgyagygguzntrouhiweuomqptfjjqsxlbylhwtpssdlltgubczxslqjgxuqnmpynnlwjgmebrpokxjnbiltvbebyytnnjlcwyzignmhedwqbfdepqakrelrdfesqrumptwwgifmmbepiktxavhuavlfaqxqhreznbvvlakzeoomykkzftthoemqwliednfsqcnbexbimrvkdhllcesrlhhjsspvfupxwdybablotibypmjutclgjurbmhztboqatrdwsomnxnmocvixxvfiqwmednahdqhxjkvcyhpxxdmzzuyyqdjibvmfkmonfxmohhshpkhmntnoplphqyprveyfsmsxjfosmicdsjrieeytpnbhlsziwxnpmgoxneqbnufhfwrjbqcsdfarybzwaplmxckkgclvwqdbpumsmqkswmjwnkuqbicykoisqwoootrdpdvcuiuswfqmrkctsgrevcxnyncmivsxbpbxzxpwchiwtkroqisnmrbmefbmatmdknaklpgpyqlsccgunaibsloyqpnsibwuowebomrmcegejozypjzjunjmeygozcjqbnrpakdermjcckartbcppmbtkhkmmtcngteigjnxxyzaibtdcwutkvpwezisskfaeljmxyjwykwglqlnofhycwuivdbnpintuyhtyqpwaoelgpbuwiuyeqhbvkqlsfgmeoheexbhnhutxvnvfjwlzfmvpcghiowocdsjcvqrdmkcizxnivbianfpsnzabxqecinhgfyjrjlbikrrgsbgfgyxtzzwwpayapfgueroncpxogouyrdgzdfucfrywtywjeefkvtzxlwmrniselyeodysirqflpduvibfdvedgcrzpzrunpadvawfsmmddqzaaahfxlifobffbyzqqbtlcpquedzjvykvarayfldvmkapjcfzfbmhscdwhciecsbdledspgpdtsteuafzbrjuvmsfrajtulwirzagiqjdiehefmfifocadxfuxrpsemavncdxuoaetjkavqicgndjkkfhbvbhjdcygfwcwyhpirrfjziqonbyxhibelinpllxsjzoiifscwzlyjdmwhnuovvugfhvquuleuzmehggdfubpzolgbhwyeqekzccuypaspozwuhbzbdqdtejuniuuyagackubauvriwneeqfhtwkocuipcelcfrcjcymcuktegiikyosumeioatfcxrheklookaqekljtvtdwhxsteajevpjviqzudnjnqbucnfvkybggaybebljwcstmktgnipdyrxbgewqczzkaxmeazpzbjsntltjwlmuclxirwytvxgvxscztryubtjweehapvxrguzzsatozzjytnamfyiitreyxmanhzeqwgpoikcjlokebksgkaqetverjegqgkicsyqcktmwjwakivtsxjwrgakphqincqrxqhzbcnxljzwturmsaklhnvyungjrxaonjqomdnxpnvihmwzphkyuhwqwdboabepmwgyatyrgtboiypxfavbjtrgwswyvcqhzwibpisydtmltbkydhznbsvxktyfxopwkxzbftzknnwipghuoijrbgqnzovxckvojvsqqraffwowfvqvfcmiicwitrhxdeombgesxexedlakitfovtydxunqnwqqdeeekiwjnwoshqcsljiicgobbbuqakjdonjawgjlezdnqhfdqnmsuavxdpnfzwipmspiabveaarshzwxmirgkmfncvtdrdvfxkpxlkdokxgtwcskmjryyymcthfnkasinihaunohkxaibtsqelockaefjmsuolebtnepauwmrxutspjwaxbmahsjtkfkxlnszribmeofbkyvbjscjtqjakuwvcgunvnonvqbbggfshauqsyznokqbhowjusypfnecffenojfvlblgzntqzlrgzprvhqnpfrrkzxznieiuivajivzijsqijigtatifmbplzqahuidegfoobpymkputzamzvweiyvvzlwihgmmmrcburbgbsdxrfjsbiylitghgcpqjbevvgypxcybubyoijijrhuzcdijfybqbfowlookqmlnplbxvjjosfqviygqyhgamuwzjklbyzopkrnhbywtfoqomweldmlrhjqswctubiknzzvcztyehouvnyiqnvkufaobehxhrjvtisxjlxoumipzjarwvbsaegdkpbsjmpevjbewzuqnfhoohhmdjgfpmjzdmtmykqvtucptwfidpwtwffzolffzqfdearclkyeecuzabjeqhxpmfodsvisnpxrqowdawheydfyhoexvcmihdlzavtqlshdhdgjzpozvvackebhgqppvcrvymljfvooauxcjnbejdivikcoaugxwzsulgfqdtefpehbrlhaoqxwcancuvbqutnfbuygoemditeagmcveatgaikwflozgdhkyfqmjcruyyuemwbqwxyyfiwnvlmbovlmccaoguieu";
//        String t = "cjgamyzjwxrgwedhsexosmswogckohesskteksqgrjonnrwhywxqkqmywqjlxnfrayykqotkzhxmbwvzstrcjfchvluvbaobymlrcgbbqaprwlsqglsrqvynitklvzmvlamqipryqjpmwhdcsxtkutyfoiqljfhxftnnjgmbpdplnuphuksoestuckgopnlwiyltezuwdmhsgzzajtrpnkkswsglhrjprxlvwftbtdtacvclotdcepuahcootzfkwqhtydwrgqrilwvbpadvpzwybmowluikmsfkvbebrxletigjjlealczoqnnejvowptikumnokysfjyoskvsxztnqhcwsamopfzablnrxokdxktrwqjvqfjimneenqvdxufahsshiemfofwlyiionrybfchuucxtyctixlpfrbngiltgtbwivujcyrwutwnuajcxwtfowuuefpnzqljnitpgkobfkqzkzdkwwpksjgzqvoplbzzjuqqgetlojnblslhpatjlzkbuathcuilqzdwfyhwkwxvpicgkxrxweaqevziriwhjzdqanmkljfatjifgaccefukavvsfrbqshhswtchfjkausgaukeapanswimbznstubmswqadckewemzbwdbogogcysfxhzreafwxxwczigwpuvqtathgkpkijqiqrzwugtr";

//        String s = "ADOBECODEBANC";
//        String t = "ABC";

//        String s = "a";
//        String t = "a";

        String s = "bba";
        String t = "ab";

//        String s =  "cabwefgewcwaefgcf";
//        String t = "cae";

        System.out.println(minWindowTicksOptimize(s, t));
    }

    public static String minWindow(String s, String t) {
        //min length of window = t.length();
        if (t.length() > s.length()) return "";


        //store all windows
        //order of min
        PriorityQueue<String> w = new PriorityQueue<>(comparingInt(String::length));


        //traverse s to find t
        //left & right pointer
        //skip anything t doesn't have
        int left = 0;
        int right = t.length();
        List<Character> tChar = new ArrayList<>();

        //populate char list
        for (Character c : t.toCharArray()) tChar.add(c);

        //traverse
        while (right <= s.length()) {
            String sub = s.substring(left, right);
            if (sub.length() < t.length()) break;
            if (valid(sub, new ArrayList<>(tChar))) {
                w.offer(sub);
                left++;
            } else {
                right++;
            }
        }

        //get minimum size
        return w.isEmpty() ? "" : w.poll();
    }

    public static String minWindowTicks(String s, String t) {
        //min length of window = t.length();
        if (t.length() > s.length()) return "";

        //store all windows
        //order of min
        PriorityQueue<int[]> w = new PriorityQueue<>(Comparator.comparingInt((set) -> (set[1] - set[0])));


        List<Character> tArray = new ArrayList<>();
        List<Integer> stMap = new ArrayList<>();

        //populate char list
        for (Character c : t.toCharArray()) tArray.add(c);

        for (int i = 0; i < s.length(); i++) if (tArray.contains(s.charAt(i))) stMap.add(i);
        if (stMap.isEmpty()) return "";


        for (int start = 0; start < stMap.size(); start++) {

            for (int end = start; end < stMap.size(); end++) {
                String sub = s.substring(stMap.get(start), stMap.get(end) + 1);
                if (sub.length() < t.length()) continue;
                if (valid(sub, new ArrayList<>(tArray))) {
                    w.offer(new int[]{stMap.get(start), stMap.get(end)});
                    break;
                }
            }
        }


        //get minimum size
        return w.isEmpty() ? "" : s.substring(w.peek()[0], w.peek()[1] + 1);
    }

    public static String minWindowTicksOptimize(String s, String t) {
        //min length of window = t.length();
        if (t.length() > s.length()) return "";

        //store all windows
        //order of min
        String window = "";

        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> has = new HashMap<>();

        //populate need list
        for (Character c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
            has.put(c, 0);
        }

        int start = 0;
        int end = 0;
        boolean expand = true;

        while (start < s.length()) {

            if (expand) {
                Character next = s.charAt(end);
                if (need.containsKey(next)) has.put(next, has.get(next) + 1);
            } else {

                //min of 0
                //reduce occurrence
                Character prev = s.charAt(start - 1);
                if (need.containsKey(prev)) has.put(prev, Math.max(0, has.get(prev) - 1));
            }


            if (valid(has, need)) {
                if (window.equals("") || (end - start) < window.length() - 1) window = s.substring(start, end + 1);

                //shrink window
                start++;
                expand = false;
            } else {

                //expand window
                if (end + 1 != s.length()) {
                    end++;
                    expand = true;
                }

                //no more expanding window
                else break;
            }
        }

        //get minimum size

        return window;
    }


    private static boolean valid(HashMap<Character, Integer> has, HashMap<Character, Integer> need) {
        for (Map.Entry<Character, Integer> e : has.entrySet()) {
            Integer have = e.getValue();
            Integer want = need.get(e.getKey());

            if (have < want) return false;
        }
        return true;
    }

    private static boolean valid(String subString, List<Character> t) {
        if (subString.length() < t.size()) return false;
        for (Character c : subString.toCharArray()) t.remove(c);
        return t.isEmpty();
    }


}
