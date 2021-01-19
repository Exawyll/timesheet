import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ActivityUserComponent } from './activity-user.component';

@Injectable({ providedIn: 'root' })
export class ActivityUserResolve {
  constructor() {}
}

export const activityRoute: Routes = [
  {
    path: '',
    component: ActivityUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Activities',
    },
    canActivate: [UserRouteAccessService],
  },
];
