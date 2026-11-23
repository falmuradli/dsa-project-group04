package model;

public class PackageTree {

    private Node root;


    private static class Node {
        private Package value;

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
            ID'sini hazirki ile muqayise edirik. Boyukdurse musbet, Kicikdirse menfi, Beraberdirse 0 qaytaracaq */

            if(comparison == 0) return false; // duplicate id

            if((comparison > 0)) { /* muqayisenin neticesi musbet oldu ona gore gedib hazirki node'un sagina baxacagiq*/
                if(current.right == null) { // eger sag node bos olarsa, sag node'a insert et
                    current.right = new Node(parcel);
                    return true;
                }
                current = current.right; // saga insert elediyimiz node'a point ele

            } else if((comparison < 0)) { // muqayisenin neticesi menfi oldu ona gore gedib hazirki node'un soluna baxacagiq
                if(current.left == null) {
                    current.left = new Node(parcel);
                    return true;
                }
                current = current.left; // sola insert elediyimiz node'a point ele
            }


        }

    }

    public Package findById(String id) {
        Node current = root;

        while (current != null) {

            int comparison = id.compareTo(current.value.getId());

            if(comparison == 0) return current.value;

            if(comparison > 0) {
                current = current.right;
            } else {
                current = current.left;
            }

        }

        return null;
    }

    public boolean deleteById(String id) {
        Node current = root;
        Node parent = null;

        while (current != null) {

            int comparison = id.compareTo(current.value.getId());

            if (comparison == 0) break;

            parent = current;
            if (comparison > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }

        if (current == null) return false;

        if (current.right != null && current.left != null) {
            Node successorParent = current;
            Node successor = current.right;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            current.value = successor.value;

            if (successorParent == current) {
                current.right = successor.right;
            } else {
                successorParent.left = successor.right;
            }

            return true;
        }

        Node replacement;
        if(current.left != null) {
            replacement = current.left;
        } else {
            replacement = current.right;
        }

        if(parent == null) {
            root = replacement;
        } else if (parent.left == current) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }

        return true;
    }
}
