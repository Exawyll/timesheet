import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesheetSharedModule } from 'app/shared/shared.module';
import { activityRoute } from './activity-user.route';
import { ActivityUserComponent } from './activity-user.component';

@NgModule({
  imports: [TimesheetSharedModule, RouterModule.forChild(activityRoute)],
  declarations: [ActivityUserComponent],
})
export class TimesheetActivityUserModule {}
