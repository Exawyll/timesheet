import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.TimesheetEmployeeModule),
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.TimesheetProjectModule),
      },
      {
        path: 'week',
        loadChildren: () => import('./week/week.module').then(m => m.TimesheetWeekModule),
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.TimesheetActivityModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TimesheetEntityModule {}
