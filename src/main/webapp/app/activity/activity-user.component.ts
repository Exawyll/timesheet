import { Component, OnInit, OnDestroy } from '@angular/core';
import { IActivity } from '../shared/model/activity.model';
import { Subscription } from 'rxjs';
import { ActivityUserService } from './activity-user.service';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { UserService } from 'app/core/user/user.service';
import { IUser } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-activity',
  templateUrl: './activity-user.component.html',
  styleUrls: ['./activity-user.component.scss'],
})
export class ActivityUserComponent implements OnInit, OnDestroy {
  activities?: IActivity[];
  eventSubscriber?: Subscription;
  userId?: IUser | undefined;

  constructor(
    protected userService: UserService,
    protected activityUserService: ActivityUserService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.activityUserService
      .getUserId()
      .subscribe(user =>
        this.activityUserService
          .query({ 'userId.equals': user?.id })
          .subscribe((res: HttpResponse<IActivity[]>) => (this.activities = res.body || []))
      );
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInActivities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IActivity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInActivities(): void {
    this.eventSubscriber = this.eventManager.subscribe('activityListModification', () => this.loadAll());
  }
}
