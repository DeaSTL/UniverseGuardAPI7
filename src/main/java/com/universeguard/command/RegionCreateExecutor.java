/* 
 * Copyright (C) JimiIT92 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Jimi, December 2017
 * 
 */
package com.universeguard.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.DimensionType;

import com.universeguard.region.LocalRegion;
import com.universeguard.region.components.RegionLocation;
import com.universeguard.region.enums.RegionText;
import com.universeguard.utils.MessageUtils;
import com.universeguard.utils.RegionUtils;

/**
 * 
 * Command Handler for /rg create
 * @author Jimi
 *
 */
public class RegionCreateExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(args.hasAny("name") && RegionUtils.hasPendingRegion(src)) {
			LocalRegion selectedRegion = (LocalRegion) RegionUtils.getPendingRegion(src);
			Player player = (Player) src;

			RegionLocation firstSelectedPoint = selectedRegion.getFirstPoint();
			RegionLocation secondSelectedPoint = selectedRegion.getSecondPoint();


			DimensionType dimension = player.getWorld().getDimension().getType();
			String world = player.getWorld().getName();
			String name = args.<String>getOne("name").get();
			RegionLocation firstPoint = new RegionLocation(
					firstSelectedPoint.getX(),
					firstSelectedPoint.getY(),
					firstSelectedPoint.getZ(),
					dimension.getId(),
					world
			);
			RegionLocation secondPoint = new RegionLocation(
					secondSelectedPoint.getX(),
					secondSelectedPoint.getY(),
					secondSelectedPoint.getZ(),
					dimension.getId(),
					world
			);
			LocalRegion region = new LocalRegion(name, firstPoint, secondPoint, false);
			if(RegionUtils.save(region))
				MessageUtils.sendSuccessMessage(src, RegionText.REGION_SAVED.getValue());
		}
		if(!RegionUtils.hasPendingRegion(src)){
			MessageUtils.sendErrorMessage(src, RegionText.NO_PENDING_REGION.getValue());
		}
		return CommandResult.empty();
	}
}