import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesheetSharedModule } from 'app/shared/shared.module';
import { WeekComponent } from './week.component';
import { WeekDetailComponent } from './week-detail.component';
import { WeekUpdateComponent } from './week-update.component';
import { WeekDeleteDialogComponent } from './week-delete-dialog.component';
import { weekRoute } from './week.route';

@NgModule({
  imports: [TimesheetSharedModule, RouterModule.forChild(weekRoute)],
  declarations: [WeekComponent, WeekDetailComponent, WeekUpdateComponent, WeekDeleteDialogComponent],
  entryComponents: [WeekDeleteDialogComponent],
})
export class TimesheetWeekModule {}
