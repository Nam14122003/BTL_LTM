/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.helper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkWithFile {

    public static boolean write(String filepath, String s) {
        try (DataOutputStream os = new DataOutputStream(new FileOutputStream(filepath, false))) {
            os.writeUTF(s);
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WorkWithFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WorkWithFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static String read(String filepath) {
        try (DataInputStream os = new DataInputStream(new FileInputStream(filepath))) {
            String result = os.readUTF();
            return result;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WorkWithFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WorkWithFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
