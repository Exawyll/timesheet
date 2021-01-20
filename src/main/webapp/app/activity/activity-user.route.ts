import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActivity, Activity } from 'app/shared/model/activity.model';
import { ActivityUserService } from './activity-user.service';
import { ActivityUserComponent } from './activity-user.component';
import { ActivityUserUpdateComponent } from './activity-user-update.component';

@Injectable({ providedIn: 'root' })
export class ActivityResolve implements Resolve<IActivity> {
  constructor(private service: ActivityUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((activity: HttpResponse<Activity>) => {
          if (activity.body) {
            return of(activity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Activity());
  }
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
  {
    path: 'new',
    component: ActivityUserUpdateComponent,
    resolve: {
      activity: ActivityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Activities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActivityUserUpdateComponent,
    resolve: {
      activity: ActivityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Activities',
    },
    canActivate: [UserRouteAccessService],
  },
];
