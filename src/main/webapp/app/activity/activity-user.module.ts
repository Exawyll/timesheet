import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesheetSharedModule } from 'app/shared/shared.module';
import { activityRoute } from './activity-user.route';
import { ActivityUserComponent } from './activity-user.component';
import { ActivityDeleteDialogComponent } from 'app/entities/activity/activity-delete-dialog.component';
import { ActivityUserUpdateComponent } from './activity-user-update.component';

@NgModule({
  imports: [TimesheetSharedModule, RouterModule.forChild(activityRoute)],
  declarations: [ActivityUserComponent, ActivityUserUpdateComponent],
  entryComponents: [ActivityDeleteDialogComponent],
})
export class TimesheetActivityUserModule {}
