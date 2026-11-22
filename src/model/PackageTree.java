package model;

public class PackageTree {

    private Node root;


    private static class Node {
        private final Package value;

        private Node left;

        private Node right;


        Node(Package value) {
            this.value = value;
        }
    }

    public boolean insert(Package parcel) {
        Node current = root;

        if(root == null) {
            root = new Node(parcel);
            return true;
        } //tree bosdusa insert ele true qaytar birbasa

        while (true) { // eger tree bos deyilse

            int comparison = parcel.getId().compareTo(current.value.getId()); /* insert elemek istediyimiz parcelin
            ID'sini hazirki ile muqayise edirik. Boyukdurse 1, Kicikdirse -1, Beraberdirse 0 qaytaracaq */

            if(comparison == 0) return false; // duplicate id

            if((comparison > 0)) { /* muqayisenin neticesi 1 oldu ona gore gedib hazirki node'un sagina baxacagiq*/
                if(current.right == null) { // eger sag node bos olarsa, sag node'a insert et
                    current.right = new Node(parcel);
                    return true;
                }
                current = current.right; // saga insert elediyimiz node'a point ele

            } else if((comparison < 0)) { // muqayisenin neticesi -1 oldu ona gore gedib hazirki node'un soluna baxacagiq//
                if(current.left == null) {
                    current.left = new Node(parcel);
                    return true;
                }
                current = current.left; // sola insert elediyimiz node'a point ele
            }


        }

    }
}
