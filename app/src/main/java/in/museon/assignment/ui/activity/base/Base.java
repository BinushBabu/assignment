/*
 *
 * Copyright (c) 2017.  dev.cobb
 *   <p>
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *   <p>
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   <p>
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package in.museon.assignment.ui.activity.base;

import android.support.v7.app.AppCompatActivity;

import in.museon.assignment.ui.dialog.DialogCallback;
import in.museon.assignment.ui.dialog.DialogParams;


/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */
public interface Base {


        void setUpToolbar();

        void setLogoInToolbar(int resourceId);

        void setToolbarColor(int color);

        void setToolBarVisibility(boolean visibility);

        void setToolBarTitle(String title);

        void setAppTitleVisibility(boolean visibility);

        void toggleActionBarMenuVisibility(boolean visible);

        void injectViews(AppCompatActivity activity);

        void destroyViews();

        void doubleTapExit();

        void checkPermissionGraded(String... request_permissions);

        void showAlertDialog(final DialogParams params,
                             final DialogCallback dgCallback);


}
