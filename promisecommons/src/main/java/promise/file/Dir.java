/*
 *
 *  * Copyright 2017, Peter Vincent
 *  * Licensed under the Apache License, Version 2.0, Promise.
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package promise.file;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import promise.data.log.LogUtil;

public class Dir {

    private static String TAG = LogUtil.makeTag(Dir.class);

    private String name;

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static String getState() {
        return Environment.getExternalStorageState();
    }

    public static boolean isWritable() {
        return Environment.MEDIA_MOUNTED.equals(getState());
    }

    public Dir(String name) {
        map(name);
    }

    public void map(String... dirs) {
        String path = Environment.getExternalStorageDirectory().toString();
        for (String dir: dirs) {
            setName(path + File.separator + dir);
            if (make(getName())) setName(getName());
        }
    }

    public static boolean make(String path) {
        File file = new File(path);
        if (!file.exists()) return file.mkdirs();
        return true;
    }

    public String path(String name) {
        return getName() + File.separator + name;
    }

    public static void clearAppData(Context context) {
        try {
            String packageName = context.getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getFile(String path, String fileName) {
        return new File(path + File.separator + fileName);
    }

    public static void move(String inputPath, String inputFile, String outputPath) {
        copy(inputPath, inputFile, outputPath);
        delete(inputPath, inputFile);
    }

    public static void delete(String inputPath, String inputFile) {
        try {
            new File(inputPath + File.separator + inputFile).delete();
        }
        catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    public static void copy(String inputPath, String inputFile, String outputPath) {
        InputStream in;
        OutputStream out;
        try {
            LogUtil.e(TAG, "origin", inputPath + File.separator + inputFile);
            in = new FileInputStream(inputPath + File.separator + inputFile);
            out = new FileOutputStream(new Dir(outputPath).getName() + File.separator + inputFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
            in.close();
            // write the output file
            out.flush();
            out.close();
            LogUtil.e(TAG, "destination", outputPath + File.separator + inputFile);
        }
        catch (FileNotFoundException fnfe1) { LogUtil.e(TAG, fnfe1.getMessage()); }
        catch (Exception e) { LogUtil.e(TAG, e.getMessage()); }
    }

    public static File save(InputStream in, File file) {
        try {
            BufferedInputStream input = new BufferedInputStream(in);
            OutputStream output = new FileOutputStream(file);

            byte[] data = new byte[1024];

            long total = 0;
            int count;

            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        }
        catch (Exception e) { LogUtil.e(TAG, e.getMessage()); }
        return file;
    }
}
